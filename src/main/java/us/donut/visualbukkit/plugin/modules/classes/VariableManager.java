package us.donut.visualbukkit.plugin.modules.classes;

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
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("UnstableApiUsage")
public class VariableManager {

    private static Map<String, Object> nonPersistentVariables = new ConcurrentHashMap<>();
    private static Map<String, Object> persistentVariables = new ConcurrentHashMap<>();
    private static HashFunction hashFunction = Hashing.md5();
    private static File dataFile;
    private static YamlConfiguration dataConfig;

    public static void loadVariables(JavaPlugin plugin) {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        if (!dataFile.exists()) {
            try {
                dataFile.getParentFile().mkdir();
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Bukkit.getScheduler().runTaskTimer(plugin, VariableManager::saveVariables, 18000, 18000);
        persistentVariables.putAll(dataConfig.getValues(true));
    }

    public static void saveVariables() {
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

    public static Object getValue(boolean persistent, Iterable<Object> args) {
        return (persistent ? persistentVariables : nonPersistentVariables).get(getVariableString(args));
    }

    public static void setValue(boolean persistent, Object value, Iterable<Object> args) {
        if (value != null) {
            (persistent ? persistentVariables : nonPersistentVariables).put(getVariableString(args), value);
        } else {
            (persistent ? persistentVariables : nonPersistentVariables).remove(getVariableString(args));
        }
    }

    public static void addToValue(boolean persistent, Object delta, Iterable<Object> args) {
        setValue(persistent, addToObject(getValue(persistent, args), delta), args);
    }

    public static void removeFromValue(boolean persistent, Object delta, Iterable<Object> args) {
        setValue(persistent, removeFromObject(getValue(persistent, args), delta), args);
    }

    private static String getVariableString(Iterable<Object> args) {
        StringBuilder variable = new StringBuilder();
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

    private static Object addToObject(Object object, Object delta) {
        if (object instanceof List) {
            ((List) object).add(delta);
            return object;
        } else if (object instanceof Number && delta instanceof Number) {
            return ((Number) object).doubleValue() + ((Number) delta).doubleValue();
        } else if (object instanceof Duration && delta instanceof Duration) {
            return ((Duration) object).plus((Duration) delta);
        }
        throw new IllegalArgumentException("Cannot add " + delta + " to " + object);
    }

    private static Object removeFromObject(Object object, Object delta) {
        if (object instanceof List) {
            ((List<?>) object).remove(delta);
            return object;
        } else if (object instanceof Number && delta instanceof Number) {
            return ((Number) object).doubleValue() - ((Number) delta).doubleValue();
        } else if (object instanceof Duration && delta instanceof Duration) {
            return ((Duration) object).minus((Duration) delta);
        }
        throw new IllegalArgumentException("Cannot remove " + delta + " from " + object);
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
