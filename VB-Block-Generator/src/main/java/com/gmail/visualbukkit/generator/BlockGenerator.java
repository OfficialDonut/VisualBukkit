package com.gmail.visualbukkit.generator;

import com.google.common.hash.Hashing;
import com.google.common.reflect.ClassPath;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class BlockGenerator {

    private Map<String, JSONObject> blockMap = new TreeMap<>();
    private Map<String, String> langMap = new TreeMap<>();

    private Path dir;
    private Path blocksFile;
    private Path langFile;

    private Map<Class<?>, String> classNames = new HashMap<>();
    private Set<String> blacklist = new HashSet<>();
    private String category;
    private String pluginModule;

    private static Class<?> eventClass;

    static {
        try {
            eventClass = Class.forName("org.bukkit.event.Event");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public BlockGenerator(Path dir, Path blocksFile, Path langFile) throws IOException {
        this.dir = dir;
        this.blocksFile = blocksFile;
        this.langFile = langFile;

        if (Files.exists(blocksFile)) {
            for (Object obj : new JSONArray(String.join("\n", Files.readAllLines(blocksFile)))) {
                JSONObject json = (JSONObject) obj;
                blockMap.put(json.getString("id"), json);
            }
        }

        if (Files.exists(langFile)) {
            for (String line : Files.readAllLines(langFile)) {
                int i = line.indexOf("=");
                langMap.put(line.substring(0, i), line.substring(i + 1));
            }
        }
    }

    public void writeFiles() throws IOException {
        if (Files.notExists(dir)) {
            Files.createDirectories(dir);
        }

        JSONArray blockArray = new JSONArray();
        blockMap.values().forEach(blockArray::put);

        StringJoiner langString = new StringJoiner("\n");
        langMap.forEach((key, value) -> langString.add(key + "=" + value));

        Files.write(blocksFile, blockArray.toString(2).getBytes(StandardCharsets.UTF_8));
        Files.write(langFile, langString.toString().getBytes(StandardCharsets.UTF_8));
    }

    @SuppressWarnings("UnstableApiUsage")
    public void generate(String packageName) throws IOException {
        for (ClassPath.ClassInfo classInfo : ClassPath.from(ClassLoader.getSystemClassLoader()).getTopLevelClasses(packageName)) {
            Class<?> clazz = classInfo.load();
            generatePackageClass(clazz);
            for (Class<?> innerClass : clazz.getDeclaredClasses()) {
                generatePackageClass(innerClass);
            }
        }
    }

    private void generatePackageClass(Class<?> clazz) {
        if (!clazz.isAnonymousClass() && !clazz.isAnnotationPresent(Deprecated.class) && !blacklist.contains(clazz.toString())) {
            generate(clazz);
        }
    }

    public void generate(Class<?> clazz) {
        if (eventClass.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
            String id = hash(clazz.toString());
            if (!blockMap.containsKey(id)) {
                JSONObject json = new JSONObject();
                json.put("id", id);
                json.put("event", clazz.getName());
                json.putOpt("plugin-module", pluginModule);
                blockMap.put(id, json);
            }
            if (category != null) {
                langMap.putIfAbsent(id + ".category", category);
            }
        } else {
            for (Constructor<?> constructor : clazz.getConstructors()) {
                if (Modifier.isPublic(constructor.getModifiers()) && !constructor.isAnnotationPresent(Deprecated.class) && !blacklist.contains(constructor.toString()) && !blacklist.contains(constructor.getName())) {
                    String id = hash(constructor.toString());
                    if (!blockMap.containsKey(id)) {
                        blockMap.put(id, generate(constructor, id));
                    }
                    langMap.putIfAbsent(id + ".title", "New " + getClassName(clazz));
                    if (category != null) {
                        langMap.putIfAbsent(id + ".category", category);
                    }
                }
            }
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && !method.isAnnotationPresent(Deprecated.class) && !blacklist.contains(method.toString()) && !blacklist.contains(method.getName())) {
                String id = hash(method.toString());
                if (!blockMap.containsKey(id)) {
                    JSONObject json = generate(method, id);
                    json.put("method", method.getName());
                    if (method.getReturnType() != void.class) {
                        json.put("return", method.getReturnType().getName());
                    }
                    if (Modifier.isStatic(method.getModifiers())) {
                        json.put("static", true);
                    }
                    blockMap.put(id, json);
                }
                langMap.putIfAbsent(id + ".title", "[" + getClassName(clazz) + "] " + formatLowerCamelCase(method.getName()));
                if (category != null) {
                    langMap.putIfAbsent(id + ".category", category);
                }
            }
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers()) && !field.isAnnotationPresent(Deprecated.class) && !blacklist.contains(field.toString())) {
                String id = hash(field.toString());
                if (!blockMap.containsKey(id)) {
                    JSONObject json = new JSONObject();
                    json.put("id", id);
                    json.put("class", clazz.getName());
                    json.put("field", field.getName());
                    json.put("return", field.getType().getName());
                    json.putOpt("plugin-module", pluginModule);
                    if (Modifier.isStatic(field.getModifiers())) {
                        json.put("static", true);
                    } else {
                        langMap.computeIfAbsent(id + ".parameters", k -> getClassName(clazz));
                    }
                    blockMap.put(id, json);
                }
                langMap.putIfAbsent(id + ".title", "[" + getClassName(clazz) + "] " + field.getName());
                if (category != null) {
                    langMap.putIfAbsent(id + ".category", category);
                }
            }
        }
    }

    private JSONObject generate(Executable executable, String id) {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("class", executable.getDeclaringClass().getName());
        json.putOpt("plugin-module", pluginModule);
        for (Class<?> parameterClass : executable.getParameterTypes()) {
            json.append("parameters", parameterClass.getName());
        }
        if (executable.getParameterCount() > 0 || (!Modifier.isStatic(executable.getModifiers()) && !eventClass.isAssignableFrom(executable.getDeclaringClass()))) {
            langMap.computeIfAbsent(id + ".parameters", k -> getParameterNames(executable));
        }
        return json;
    }

    private String getParameterNames(Executable executable) {
        StringJoiner joiner = new StringJoiner(",");
        if (!(executable instanceof Constructor) && !Modifier.isStatic(executable.getModifiers()) && !eventClass.isAssignableFrom(executable.getDeclaringClass())) {
            joiner.add(getClassName(executable.getDeclaringClass()));
        }
        for (Parameter parameter : executable.getParameters()) {
            joiner.add(formatLowerCamelCase(parameter.getName()));
        }
        return joiner.toString();
    }

    private String getClassName(Class<?> clazz) {
        return eventClass.isAssignableFrom(clazz) ? clazz.getSimpleName() : classNames.computeIfAbsent(clazz, k -> formatUpperCamelCase(k.getSimpleName()));
    }

    private String formatUpperCamelCase(String str) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            builder.append(c);
            if (i + 1 < str.length()
                    && Character.isUpperCase(str.charAt(i + 1))
                    && (Character.isLowerCase(c)
                    || (i + 2 < str.length() && Character.isLowerCase(str.charAt(i + 2))))) {
                builder.append(' ');
            }
        }
        return builder.toString();
    }

    private String formatLowerCamelCase(String str) {
        return formatUpperCamelCase(Character.toUpperCase(str.charAt(0)) + str.substring(1));
    }

    public void reset() {
        category = null;
        pluginModule = null;
    }

    public void addAlias(Class<?> clazz, String alias) {
        classNames.put(clazz, alias);
    }

    public void addToBlackList(String string) {
        blacklist.add(string);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPluginModule(String pluginModule) {
        this.pluginModule = pluginModule;
    }

    @SuppressWarnings("UnstableApiUsage")
    private static String hash(String string) {
        return Hashing.murmur3_128().hashString(string, StandardCharsets.UTF_8).toString();
    }
}
