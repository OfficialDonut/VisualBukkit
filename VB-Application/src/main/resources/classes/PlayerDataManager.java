import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerDataManager implements Listener {

    private static PlayerDataManager instance = new PlayerDataManager();
    private JavaPlugin plugin = PluginMain.getInstance();
    private Path playerDataDir = plugin.getDataFolder().toPath().resolve("Player Data");
    private Map<UUID, PlayerData> playerData = new HashMap<>();
    private ExecutorService executor = Executors.newCachedThreadPool();

    private PlayerDataManager() {
        try {
            Files.createDirectories(playerDataDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayerDataManager getInstance() {
        return instance;
    }

    public void setData(OfflinePlayer player, String variable, Object value) {
        getPlayerData(player).set(variable, value);
    }

    public Object getData(OfflinePlayer player, String variable) {
        return getPlayerData(player).get(variable);
    }

    public void saveAllData() {
        playerData.values().forEach(PlayerData::save);
        executor.shutdown();
    }

    private PlayerData getPlayerData(OfflinePlayer player) {
        PlayerData data = playerData.computeIfAbsent(player.getUniqueId(), PlayerData::new);
        if (!player.isOnline()) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (!player.isOnline()) {
                    playerData.remove(player.getUniqueId());
                    executor.execute(data::save);
                }
            }, 6000);
        }
        return data;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerLoginEvent e) {
        if (e.getResult() == PlayerLoginEvent.Result.ALLOWED) {
            playerData.computeIfAbsent(e.getPlayer().getUniqueId(), PlayerData::new);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        PlayerData data = playerData.remove(e.getPlayer().getUniqueId());
        if (data != null) {
            executor.execute(data::save);
        }
    }

    private class PlayerData {

        private UUID uuid;
        private Path configFile;
        private YamlConfiguration config;
        private boolean isLoaded;

        private PlayerData(UUID uuid) {
            this.uuid = uuid;
            executor.execute(this::load);
        }

        private void load() {
            configFile = playerDataDir.resolve(uuid + ".yml");
            config = YamlConfiguration.loadConfiguration(configFile.toFile());
            synchronized (this) {
                isLoaded = true;
                notifyAll();
            }
        }

        private synchronized void waitForLoad() {
            while (!isLoaded) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void set(String variable, Object value) {
            waitForLoad();
            config.set(variable, value);
        }

        private Object get(String variable) {
            waitForLoad();
            return config.get(variable);
        }

        private void save() {
            try {
                waitForLoad();
                if (config.getKeys(false).isEmpty()) {
                    Files.deleteIfExists(configFile);
                } else {
                    config.save(configFile.toFile());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
