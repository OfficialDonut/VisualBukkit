import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class PlayerDataManager implements Listener {

    private static PlayerDataManager instance = new PlayerDataManager();
    private JavaPlugin plugin = PluginMain.getInstance();
    private File playerDataDir = new File(plugin.getDataFolder(), "Player Data");
    private Map<UUID, PlayerData> playerData = new HashMap<>();
    private ExecutorService executor = Executors.newCachedThreadPool();

    private PlayerDataManager() {
        instance = this;
        playerDataDir.mkdirs();
    }

    public void setData(Player player, String variable, Object value) {
        PlayerData data = playerData.get(player.getUniqueId());
        try {
            data.lock.acquireUninterruptibly();
            data.config.set(variable, value);
        } finally {
            data.lock.release();
        }
    }

    public Object getData(Player player, String variable) {
        PlayerData data = playerData.get(player.getUniqueId());
        try {
            data.lock.acquireUninterruptibly();
            return data.config.get(variable);
        } finally {
            data.lock.release();
        }
    }

    public void saveAllData() {
        for (Map.Entry<UUID, PlayerData> entry : playerData.entrySet()) {
            try {
                entry.getValue().lock.acquireUninterruptibly();
                saveData(entry.getKey(), entry.getValue().config);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                entry.getValue().lock.release();
            }
        }
    }

    private void saveData(UUID uuid, YamlConfiguration config) throws IOException {
        File file = new File(playerDataDir, uuid + ".yml");
        if (config.getKeys(false).isEmpty()) {
            if (file.exists()) {
                file.delete();
            }
        } else {
            config.save(file);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerLoginEvent e) {
        if (e.getResult() == PlayerLoginEvent.Result.ALLOWED) {
            PlayerData data = new PlayerData();
            data.lock.acquireUninterruptibly();
            playerData.put(e.getPlayer().getUniqueId(), data);
            executor.submit(() -> {
                try {
                    data.config = YamlConfiguration.loadConfiguration(new File(playerDataDir, e.getPlayer().getUniqueId() + ".yml"));
                } finally {
                    data.lock.release();
                }
            });
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        PlayerData data = playerData.remove(e.getPlayer().getUniqueId());
        executor.submit(() -> {
            try {
                data.lock.acquireUninterruptibly();
                saveData(e.getPlayer().getUniqueId(), data.config);
            } catch (IOException ex) {
                Bukkit.getScheduler().runTask(plugin, (Runnable) ex::printStackTrace);
            } finally {
                data.lock.release();
            }
        });
    }

    public static PlayerDataManager getInstance() {
        return instance;
    }

    private static class PlayerData {
        private Semaphore lock = new Semaphore(1);
        private YamlConfiguration config;
    }
}
