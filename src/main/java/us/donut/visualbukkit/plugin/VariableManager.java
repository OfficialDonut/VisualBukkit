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

@SuppressWarnings("UnstableApiUsage")
public class VariableManager {

    private static Map<String, Object> variables = new HashMap<>();
    private static HashFunction hashFunction = Hashing.md5();

    public static Map<String, Object> getVariables() {
        return variables;
    }

    public static String getVariableString(Object... objects) {
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

    public static void addToVariable(String variable, Object obj, Map<String, Object> variables) {
        Object variableValue = variables.get(variable);
        if (variableValue instanceof SimpleList) {
            ((SimpleList) variableValue).add(obj);
        } else if (variableValue instanceof Number && obj instanceof Number) {
            variables.put(variable, ((Number) variableValue).doubleValue() + ((Number) obj).doubleValue());
        } else if (variableValue instanceof Duration && obj instanceof Duration) {
            variables.put(variable, ((Duration) variableValue).plus((Duration) obj));
        }
    }

    public static void removeFromVariable(String variable, Object obj, Map<String, Object> variables) {
        Object variableValue = variables.get(variable);
        if (variableValue instanceof SimpleList) {
            ((SimpleList) variableValue).remove(obj);
        } else if (variableValue instanceof Number && obj instanceof Number) {
            variables.put(variable, ((Number) variableValue).doubleValue() - ((Number) obj).doubleValue());
        } else if (variableValue instanceof Duration && obj instanceof Duration) {
            variables.put(variable, ((Duration) variableValue).minus((Duration) obj));
        }
    }

    public static void loadVariables() {
        YamlConfiguration dataConfig = PluginMain.getDataConfig();
        if (dataConfig != null) {
            for (String key : dataConfig.getKeys(false)) {
                Object object = dataConfig.get(key);
                variables.put(key, object instanceof Collection ? new SimpleList(object) : object);
            }
        }
    }

    public static void saveVariables() {
        YamlConfiguration dataConfig = PluginMain.getDataConfig();
        if (dataConfig != null) {
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
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

    private static boolean isSerializable(Object object) {
        return object instanceof Serializable || object instanceof ConfigurationSerializable;
    }
}
