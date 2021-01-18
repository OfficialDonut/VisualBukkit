package com.gmail.visualbukkit.stdlib;

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
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class VariableManager {

    private static Map<Object, Map<String, Object>> localVariables = Collections.synchronizedMap(new WeakHashMap<>());
    private static Map<String, Object> globalVariables = new ConcurrentHashMap<>();
    private static Map<String, Object> persistentVariables = new ConcurrentHashMap<>();
    private static HashFunction hashFunction = Hashing.md5();
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

    public static Object getLocalVariable(Object scope, Object... args) {
        Map<String, Object> map = localVariables.get(scope);
        return map != null ? map.get(hash(args)) : null;
    }

    public static Object getVariable(boolean persistent, Object... args) {
        return (persistent ? persistentVariables : globalVariables).get(hash(args));
    }

    public static void setLocalVariable(Object scope, Object value, Object... args) {
        Map<String, Object> map = localVariables.computeIfAbsent(scope, k -> new ConcurrentHashMap<>());
        if (value != null) {
            map.put(hash(args), value);
        } else {
            map.remove(hash(args));
        }
    }

    public static void setVariable(boolean persistent, Object value, Object... args) {
        Map<String, Object> map = persistent ? persistentVariables : globalVariables;
        if (value != null) {
            map.put(hash(args), value);
        } else {
            map.remove(hash(args));
        }
    }

    public static void addToLocalVariable(Object scope, double delta, Object... args) {
        Object object = getLocalVariable(scope, args);
        if (object == null) {
            setLocalVariable(scope, delta, args);
        } else if (object instanceof Number) {
            setLocalVariable(scope, ((Number) object).doubleValue() + delta, args);
        } else {
            throw new IllegalArgumentException("Cannot add " + delta + " to " + object);
        }
    }

    public static void addToVariable(boolean persistent, double delta, Object... args) {
        Object object = getVariable(persistent, args);
        if (object == null) {
            setVariable(persistent, delta, args);
        } else if (object instanceof Number) {
            setVariable(persistent, ((Number) object).doubleValue() + delta, args);
        } else {
            throw new IllegalArgumentException("Cannot add " + delta + " to " + object);
        }
    }

    public static void removeFromLocalVariable(Object scope, double delta, Object... args) {
        addToLocalVariable(scope, -delta, args);
    }

    public static void removeFromVariable(boolean persistent, double delta, Object... args) {
        addToVariable(persistent, -delta, args);
    }

    private static String hash(Object[] args) {
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
