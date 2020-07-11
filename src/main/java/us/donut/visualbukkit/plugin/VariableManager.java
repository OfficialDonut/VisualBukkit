package us.donut.visualbukkit.plugin;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import us.donut.visualbukkit.util.SimpleList;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("UnstableApiUsage")
public class VariableManager {

    private static Map<String, Object> nonPersistentVariables = new ConcurrentHashMap<>();
    private static Map<String, Object> persistentVariables = new ConcurrentHashMap<>();
    private static HashFunction hashFunction = Hashing.md5();

    public static void loadVariables() {
        YamlConfiguration dataConfig = PluginMain.getDataConfig();
        if (dataConfig != null) {
            for (String key : dataConfig.getKeys(false)) {
                Object object = dataConfig.get(key);
                persistentVariables.put(key, object instanceof Collection ? new SimpleList(object) : object);
            }
        }
    }

    public static void saveVariables() {
        YamlConfiguration dataConfig = PluginMain.getDataConfig();
        if (dataConfig != null) {
            for (Map.Entry<String, Object> entry : persistentVariables.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof SimpleList) {
                    value = ((SimpleList) value).stream().filter(VariableManager::isSerializable).toArray();
                }
                if (isSerializable(value)) {
                    dataConfig.set(key, value);
                }
            }
            try {
                dataConfig.save(PluginMain.getDataFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object getVarValue(boolean persistent, Object... args) {
        return (persistent ? persistentVariables : nonPersistentVariables).get(getVariableString(args));
    }

    public static void setVarValue(boolean persistent, Object value, Object... args) {
        (persistent ? persistentVariables : nonPersistentVariables).put(getVariableString(args), value);
    }

    public static void deleteVar(boolean persistent, Object... args) {
        (persistent ? persistentVariables : nonPersistentVariables).remove(getVariableString(args));
    }

    public static void addToVar(boolean persistent, Object delta, Object... args) {
        Map<String, Object> map = persistent ? persistentVariables : nonPersistentVariables;
        String variableString = getVariableString(args);
        map.put(variableString, addToObject(map.get(variableString), delta));
    }

    public static void removeFromVar(boolean persistent, Object delta, Object... args) {
        Map<String, Object> map = persistent ? persistentVariables : nonPersistentVariables;
        String variableString = getVariableString(args);
        map.put(variableString, removeFromObject(map.get(variableString), delta));
    }

    public static Object addToObject(Object object, Object delta) {
        if (object instanceof SimpleList) {
            ((SimpleList) object).add(delta);
            return object;
        } else if (object instanceof Number && delta instanceof Number) {
            return ((Number) object).doubleValue() + ((Number) delta).doubleValue();
        } else if (object instanceof Duration && delta instanceof Duration) {
            return ((Duration) object).plus((Duration) delta);
        }
        throw new IllegalArgumentException("Cannot add " + delta + " to " + object);
    }

    public static Object removeFromObject(Object object, Object delta) {
        if (object instanceof SimpleList) {
            ((SimpleList) object).remove(delta);
            return object;
        } else if (object instanceof Number && delta instanceof Number) {
            return ((Number) object).doubleValue() - ((Number) delta).doubleValue();
        } else if (object instanceof Duration && delta instanceof Duration) {
            return ((Duration) object).minus((Duration) delta);
        }
        throw new IllegalArgumentException("Cannot remove " + delta + " from " + object);
    }

    private static String getVariableString(Object... args) {
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
            variable.append(object.getClass().getCanonicalName()).append(string);
        }
        return hashFunction.hashString(variable.toString(), StandardCharsets.UTF_8).toString();
    }

    private static boolean isSerializable(Object object) {
        return object instanceof Serializable || object instanceof ConfigurationSerializable;
    }
}
