import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager implements Listener {

    private static PlayerDataManager instance = new PlayerDataManager();
    private JavaPlugin plugin = PluginMain.getInstance();
    private File playerDataDir = new File(plugin.getDataFolder(), "Player Data");
    private Map<UUID, YamlConfiguration> playerConfigs = new HashMap<>();

    private PlayerDataManager() {
        instance = this;
        playerDataDir.mkdirs();
    }

    public synchronized void setData(Player player, String variable, Object value) {
        playerConfigs.get(player.getUniqueId()).set(variable, value);
    }

    public synchronized Object getData(Player player, String variable) {
        return playerConfigs.get(player.getUniqueId()).get(variable);
    }

    public synchronized void saveAllData() {
        for (Map.Entry<UUID, YamlConfiguration> entry : playerConfigs.entrySet()) {
            try {
                saveData(entry.getKey(), entry.getValue());
            } catch (IOException e) {
                e.printStackTrace();
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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                UUID uuid = e.getPlayer().getUniqueId();
                playerConfigs.put(uuid, YamlConfiguration.loadConfiguration(new File(playerDataDir, uuid + ".yml")));
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                synchronized (this) {
                    saveData(e.getPlayer().getUniqueId(), playerConfigs.remove(e.getPlayer().getUniqueId()));
                }
            } catch (IOException ex) {
                Bukkit.getScheduler().runTask(plugin, (Runnable) ex::printStackTrace);
            }
        });
    }

    public static PlayerDataManager getInstance() {
        return instance;
    }
}
