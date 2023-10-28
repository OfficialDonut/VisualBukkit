package com.gmail.visualbukkit.blocks.classes;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassRegistry {

    private static final Map<String, ClassInfo> classes = new HashMap<>();

    public static void register(ClassLoader classLoader, String resourceDir) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(resourceDir)))) {
            for (String resource = reader.readLine(); resource != null; resource = reader.readLine()) {
                try (InputStream stream = classLoader.getResourceAsStream(resourceDir + "/" + resource)) {
                    register(new JSONObject(new JSONTokener(stream)));
                }
            }
        }
    }

    public static void register(JSONObject json) {
        classes.put(json.getString("name"), new ClassInfo(json));
    }

    public static void clear() {
        classes.clear();
    }

    public static Optional<ClassInfo> getClass(String name) {
        return Optional.ofNullable(classes.get(name));
    }

    public static Set<ClassInfo> getClasses() {
        return new TreeSet<>(classes.values());
    }

    public static Set<ClassInfo> getClasses(Predicate<ClassInfo> filter) {
        return classes.values().stream().filter(filter).collect(Collectors.toCollection(TreeSet::new));
    }
}
