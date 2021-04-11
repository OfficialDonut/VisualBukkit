package com.gmail.visualbukkit.generator;

import com.google.common.hash.Hashing;
import com.google.common.reflect.ClassPath;
import org.apache.commons.lang3.ClassUtils;
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
    private boolean includeDeprecated;

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
    public void generatePackage(String packageName) throws IOException {
        for (ClassPath.ClassInfo classInfo : ClassPath.from(ClassLoader.getSystemClassLoader()).getTopLevelClasses(packageName)) {
            Class<?> clazz = classInfo.load();
            generatePackageClass(clazz);
            for (Class<?> innerClass : clazz.getDeclaredClasses()) {
                generatePackageClass(innerClass);
            }
        }
    }

    private void generatePackageClass(Class<?> clazz) {
        if (!clazz.isAnonymousClass()
                && (includeDeprecated || !clazz.isAnnotationPresent(Deprecated.class))
                && !blacklist.contains(clazz.toString())) {
            generateClass(clazz);
        }
    }

    public void generateClass(Class<?> clazz) {
        if (eventClass.isAssignableFrom(clazz)) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
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
                generateMethods(clazz, clazz.getMethods(), true);
            }
        } else {
            generateFields(clazz, clazz.getDeclaredFields());
            generateConstructors(clazz, clazz.getDeclaredConstructors());
            generateMethods(clazz, clazz.getDeclaredMethods(), false);
        }
    }

    private void generateFields(Class<?> clazz, Field[] fields) {
        for (Field field : fields) {
            if (Modifier.isPublic(field.getModifiers())
                    && (includeDeprecated || !field.isAnnotationPresent(Deprecated.class))
                    && !blacklist.contains(field.toString())) {
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
                        json.append("parameters", clazz.getName());
                        langMap.computeIfAbsent(id + ".parameters", k -> getDisplayClassName(clazz));
                    }
                    blockMap.put(id, json);
                }
                langMap.putIfAbsent(id + ".title", "[" + getDisplayClassName(clazz) + "] " + field.getName());
                if (category != null) {
                    langMap.putIfAbsent(id + ".category", category);
                }
            }
        }
    }

    private void generateConstructors(Class<?> clazz, Constructor<?>[] constructors) {
        for (Constructor<?> constructor : constructors) {
            if (Modifier.isPublic(constructor.getModifiers())
                    && (includeDeprecated || !constructor.isAnnotationPresent(Deprecated.class))
                    && !blacklist.contains(constructor.toString())
                    && !blacklist.contains(constructor.getName())) {
                String id = hash(constructor.toString());
                if (!blockMap.containsKey(id)) {
                    blockMap.put(id, generateExecutable(clazz, constructor, id));
                }
                langMap.putIfAbsent(id + ".title", "New " + getDisplayClassName(clazz));
                if (category != null) {
                    langMap.putIfAbsent(id + ".category", category);
                }
            }
        }
    }

    private void generateMethods(Class<?> clazz, Method[] methods, boolean isEvent) {
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getDeclaringClass() != Object.class
                    && (includeDeprecated || !method.isAnnotationPresent(Deprecated.class))
                    && !blacklist.contains(method.toString())
                    && !blacklist.contains(method.getName())) {
                String id = hash(method.getDeclaringClass() != clazz ? clazz + method.toString() : method.toString());
                if (!blockMap.containsKey(id)) {
                    JSONObject json = generateExecutable(clazz, method, id);
                    json.put("method", method.getName());
                    if (method.getReturnType() != void.class) {
                        json.put("return", method.getReturnType().getName());
                    }
                    if (Modifier.isStatic(method.getModifiers())) {
                        json.put("static", true);
                    }
                    if (isEvent) {
                        json.put("event-method", true);
                    }
                    blockMap.put(id, json);
                }
                langMap.putIfAbsent(id + ".title", "[" + getDisplayClassName(clazz) + "] " + formatLowerCamelCase(method.getName()));
                if (category != null) {
                    langMap.putIfAbsent(id + ".category", category);
                }
            }
        }
    }

    private JSONObject generateExecutable(Class<?> clazz, Executable executable, String id) {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("class", clazz.getName());
        json.putOpt("plugin-module", pluginModule);
        boolean isInstanceMethod = !Modifier.isStatic(executable.getModifiers()) && !(executable instanceof Constructor) && !eventClass.isAssignableFrom(clazz);
        if (isInstanceMethod) {
            json.append("parameters", clazz.getName());
        }
        for (Class<?> parameterClass : executable.getParameterTypes()) {
            json.append("parameters", parameterClass.getName());
        }
        if (executable.getParameterCount() > 0 || isInstanceMethod) {
            langMap.computeIfAbsent(id + ".parameters", k -> {
                StringJoiner joiner = new StringJoiner(",");
                if (isInstanceMethod) {
                    joiner.add(getDisplayClassName(clazz));
                }
                for (Parameter parameter : executable.getParameters()) {
                    joiner.add(formatLowerCamelCase(parameter.getName()));
                }
                return joiner.toString();
            });
        }
        return json;
    }

    private String getDisplayClassName(Class<?> clazz) {
        return classNames.computeIfAbsent(clazz, ClassUtils::getShortClassName);
    }

    private String formatLowerCamelCase(String str) {
        str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
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

    public void reset() {
        category = null;
        pluginModule = null;
        includeDeprecated = false;
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

    public void setIncludeDeprecated(boolean includeDeprecated) {
        this.includeDeprecated = includeDeprecated;
    }

    @SuppressWarnings("UnstableApiUsage")
    private static String hash(String string) {
        return Hashing.murmur3_128().hashString(string, StandardCharsets.UTF_8).toString();
    }
}
