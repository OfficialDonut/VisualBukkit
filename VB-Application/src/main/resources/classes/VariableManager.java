import com.google.common.hash.HashFunction;
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
    private static HashFunction hashFunction = Hashing.murmur3_128();
    private static File dataFile;

    public static void loadVariables(JavaPlugin plugin) {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, VariableManager::saveVariables, 18000, 18000);
        persistentVariables.putAll(YamlConfiguration.loadConfiguration(dataFile).getValues(true));
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

    public static Object getVariable(boolean persistent, String name, List<?> args) {
        return (persistent ? persistentVariables : globalVariables).get(hash(name, args));
    }

    public static void setVariable(boolean persistent, Object value, String name, List<?> args) {
        Map<String, Object> map = persistent ? persistentVariables : globalVariables;
        if (value != null) {
            map.put(hash(name, args), value);
        } else {
            map.remove(hash(name, args));
        }
    }

    public static void addToVariable(boolean persistent, double delta, String name, List<?> args) {
        Object object = getVariable(persistent, name, args);
        if (object == null) {
            setVariable(persistent, delta, name, args);
        } else if (object instanceof Number) {
            setVariable(persistent, ((Number) object).doubleValue() + delta, name, args);
        } else {
            throw new IllegalArgumentException("Cannot add " + delta + " to " + object);
        }
    }

    public static void removeFromVariable(boolean persistent, double delta, String name, List<?> args) {
        addToVariable(persistent, -delta, name, args);
    }

    private static String hash(String name, List<?> args) {
        StringBuilder variable = new StringBuilder();
        variable.append(name);
        for (Object object : args) {
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
            variable.append(object.getClass().getName()).append(string);
        }
        return hashFunction.hashString(variable.toString(), StandardCharsets.UTF_8).toString();
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
