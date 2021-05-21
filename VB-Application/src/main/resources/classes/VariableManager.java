import com.google.common.hash.Hashing;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

public class VariableManager {

    private static Map<String, Object> globalVariables = new ConcurrentHashMap<>();
    private static Map<String, Object> persistentVariables = new ConcurrentHashMap<>();
    private static File dataFile;

    public static void loadVariables(JavaPlugin plugin) {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, VariableManager::saveVariables, 18000, 18000);
        persistentVariables.putAll(YamlConfiguration.loadConfiguration(dataFile).getValues(false));
    }

    public static void saveVariables() {
        if (!persistentVariables.isEmpty()) {
            YamlConfiguration dataConfig = new YamlConfiguration();
            for (Map.Entry<String, Object> entry : persistentVariables.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Collection) {
                    value = filter((Collection<?>) value);
                }
                if (isSerializable(value)) {
                    dataConfig.set(entry.getKey(), value);
                }
            }
            try {
                dataConfig.save(dataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object getSimpleVariable(String name) {
        return persistentVariables.get(name);
    }

    public static void setSimpleVariable(String name, Object value) {
        if (value != null) {
            persistentVariables.put(name, value);
        } else {
            persistentVariables.remove(name);
        }
    }

    public static Object getVariable(boolean persistent, String name, Object arg) {
        return arg != null ? (persistent ? persistentVariables : globalVariables).get(getString(name, arg)) : null;
    }

    public static void setVariable(boolean persistent, Object value, String name, Object arg) {
        Map<String, Object> map = persistent ? persistentVariables : globalVariables;
        if (value != null) {
            map.put(getString(name, arg), value);
        } else {
            map.remove(getString(name, arg));
        }
    }

    private static String getString(String name, Object arg) {
        StringBuilder variable = new StringBuilder(name);
        if (arg instanceof Collection) {
            for (Object obj : (Collection<?>) arg) {
                variable.append(getString(obj));
            }
        } else {
            variable.append(getString(arg));
        }
        return Hashing.murmur3_128().hashString(variable.toString(), StandardCharsets.UTF_8).toString();
    }

    private static String getString(Object object) {
        String string;
        if (object instanceof Entity) {
            string = ((Entity) object).getUniqueId().toString();
        } else if (object instanceof OfflinePlayer) {
            string = ((OfflinePlayer) object).getUniqueId().toString();
        } else if (object instanceof World) {
            string = ((World) object).getUID().toString();
        } else if (object instanceof Block) {
            string = ((Block) object).getLocation().toString();
        } else {
            string = object.toString();
        }
        return object.getClass().getName() + string;
    }

    private static boolean isSerializable(Object object) {
        return object instanceof Serializable || object instanceof ConfigurationSerializable;
    }

    private static Object[] filter(Collection<?> collection) {
        return collection.stream()
                .filter(VariableManager::isSerializable)
                .map(o -> o instanceof Collection<?> ? filter((Collection<?>) o) : o)
                .toArray();
    }
}
