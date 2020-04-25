package us.donut.visualbukkit.plugin;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import us.donut.visualbukkit.util.SimpleList;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@SuppressWarnings("UnstableApiUsage")
public class PluginMain extends JavaPlugin implements Listener {

    private final Map<String, Object> variables = new HashMap<>();
    private final HashFunction hashFunction = Hashing.md5();
    private File dataFile = new File(getDataFolder(), "data.yml");
    private YamlConfiguration dataConfig = YamlConfiguration.loadConfiguration(dataFile);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        if (!dataFile.exists()) {
            save(dataConfig, dataFile);
        }
        loadVariables();
        Bukkit.getScheduler().runTaskTimer(this, this::saveVariables, 0, 18000);
    }

    @Override
    public void onDisable() {
        saveVariables();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] commandArgs) {
        return true;
    }

    public Runnable getRunnable(String procedure, Object... args) {
        return () -> procedure(procedure, args);
    }

    public void procedure(String procedure, Object... args) {}

    public Object function(String function, Object... args) {
        return null;
    }

    private void save(YamlConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String decode(String string) {
        return new String(Base64.getDecoder().decode(string), StandardCharsets.UTF_8);
    }

    private boolean checkEquals(Object o1, Object o2) {
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1 instanceof Number && o2 instanceof Number ? ((Number) o1).doubleValue() == ((Number) o2).doubleValue() : o1.equals(o2);
    }

    private String getString(Object object) {
        if (object == null) {
            return "null";
        }
        if (object instanceof OfflinePlayer) {
            return ((OfflinePlayer) object).getName();
        }
        if (object instanceof World) {
            return ((World) object).getName();
        }
        if (object instanceof Entity) {
            return ((Entity) object).getType().toString();
        }
        return object.toString();
    }

    private String color(String string) {
        return string != null ? ChatColor.translateAlternateColorCodes('&', string) : null;
    }

    private List<String> color(List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            strings.add(i, color(strings.get(i)));
            strings.remove(i + 1);
        }
        return strings;
    }

    private String getVariable(Object... objects) {
        StringBuilder variable = new StringBuilder();
        for (Object object : objects) {
            String string = object.toString();
            if (object instanceof Entity) {
                string = ((Entity) object).getUniqueId().toString();
            } else if (object instanceof OfflinePlayer) {
                string = ((OfflinePlayer) object).getUniqueId().toString();
            } else if (object instanceof World) {
                string = ((World) object).getUID().toString();
            } else if (object instanceof Block) {
                string = ((Block) object).getLocation().toString();
            }
            variable.append(object.getClass().getCanonicalName()).append(string);
        }
        return hashFunction.hashString(variable.toString(), StandardCharsets.UTF_8).toString();
    }

    private void addToVariable(String variable, Object obj, Map<String, Object> variables) {
        Object variableValue = variables.get(variable);
        if (variableValue instanceof SimpleList) {
            ((SimpleList) variableValue).add(obj);
        } else if (variableValue instanceof Number && obj instanceof Number) {
            variables.put(variable, ((Number) variableValue).doubleValue() + ((Number) obj).doubleValue());
        } else if (variableValue instanceof Duration && obj instanceof Duration) {
            variables.put(variable, ((Duration) variableValue).plus((Duration) obj));
        }
    }

    private void removeFromVariable(String variable, Object obj, Map<String, Object> variables) {
        Object variableValue = variables.get(variable);
        if (variableValue instanceof SimpleList) {
            ((SimpleList) variableValue).remove(obj);
        } else if (variableValue instanceof Number && obj instanceof Number) {
            variables.put(variable, ((Number) variableValue).doubleValue() - ((Number) obj).doubleValue());
        } else if (variableValue instanceof Duration && obj instanceof Duration) {
            variables.put(variable, ((Duration) variableValue).minus((Duration) obj));
        }
    }

    private void loadVariables() {
        for (String key : dataConfig.getKeys(false)) {
            Object object = dataConfig.get(key);
            variables.put(key, object instanceof Collection ? new SimpleList(object) : object);
        }
    }

    private void saveVariables() {
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof SimpleList) {
                value = ((SimpleList) value).stream().filter(this::isSerializable).toArray();
            }
            if (isSerializable(value)) {
                dataConfig.set(key, value);
            }
        }
        save(dataConfig, dataFile);
    }

    private boolean isSerializable(Object object) {
        return object instanceof Serializable || object instanceof ConfigurationSerializable;
    }
}
