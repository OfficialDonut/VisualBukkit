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
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

@SuppressWarnings("UnstableApiUsage")
public class VariableManager {

    private static Map<Object, Map<String, Object>> localVariables = new WeakHashMap<>();
    private static Map<String, Object> nonPersistentVariables = new HashMap<>();
    private static Map<String, Object> persistentVariables = new HashMap<>();
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

    public static Object getLocalVarValue(Object scope, String variableString) {
        return getLocalVarMap(scope).get(variableString);
    }

    public static Object getVarValue(boolean persistent, Object... args) {
        return (persistent ? persistentVariables : nonPersistentVariables).get(getVariableString(args));
    }

    public static void setLocalVarValue(Object scope, String variableString, Object value) {
        getLocalVarMap(scope).put(variableString, value);
    }

    public static void setVarValue(boolean persistent, Object value, Object... args) {
        (persistent ? persistentVariables : nonPersistentVariables).put(getVariableString(args), value);
    }

    public static void deleteLocalVar(Object scope, String variableString) {
        getLocalVarMap(scope).remove(variableString);
    }

    public static void deleteVar(boolean persistent, Object... args) {
        (persistent ? persistentVariables : nonPersistentVariables).remove(getVariableString(args));
    }

    public static void addToLocalVar(Object scope, String variableString, Object delta) {
        addToVar(getLocalVarMap(scope), variableString, delta);
    }

    public static void addToVar(boolean persistent, Object delta, Object... args) {
        addToVar(persistent ? persistentVariables : nonPersistentVariables, getVariableString(args), delta);
    }

    public static void removeFromLocalVar(Object scope, String variableString, Object delta) {
        removeFromVar(getLocalVarMap(scope), variableString, delta);
    }

    public static void removeFromVar(boolean persistent, Object delta, Object... args) {
        removeFromVar(persistent ? persistentVariables : nonPersistentVariables, getVariableString(args), delta);
    }

    private static void addToVar(Map<String, Object> variableMap, String variableString, Object delta) {
        Object value = variableMap.get(variableString);
        if (value instanceof SimpleList) {
            ((SimpleList) value).add(delta);
        } else if (value instanceof Number && delta instanceof Number) {
            variableMap.put(variableString, ((Number) value).doubleValue() + ((Number) delta).doubleValue());
        } else if (value instanceof Duration && delta instanceof Duration) {
            variableMap.put(variableString, ((Duration) value).plus((Duration) delta));
        }
    }

    private static void removeFromVar(Map<String, Object> variableMap, String variableString, Object delta) {
        Object value = variableMap.get(variableString);
        if (value instanceof SimpleList) {
            ((SimpleList) value).remove(delta);
        } else if (value instanceof Number && delta instanceof Number) {
            variableMap.put(variableString, ((Number) value).doubleValue() - ((Number) delta).doubleValue());
        } else if (value instanceof Duration && delta instanceof Duration) {
            variableMap.put(variableString, ((Duration) value).minus((Duration) delta));
        }
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

    private static Map<String, Object> getLocalVarMap(Object scope) {
        return localVariables.computeIfAbsent(scope, k -> new HashMap<>());
    }

    private static boolean isSerializable(Object object) {
        return object instanceof Serializable || object instanceof ConfigurationSerializable;
    }
}
