package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.definitions.CompEventListener;
import com.gmail.visualbukkit.gui.NotificationManager;
import com.google.common.reflect.ClassPath;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.*;

public class BlockRegistry {

    private final static Map<String, Class<?>> classCache = new HashMap<>();
    private final static Map<String, PluginComponent> pluginComponentMap = new HashMap<>();
    private final static Map<String, Statement> statementMap = new HashMap<>();
    private final static Map<String, Expression> expressionMap = new HashMap<>();
    private final static Set<PluginComponent> pluginComponents = new TreeSet<>();
    private final static Set<Statement> statements = new TreeSet<>();
    private final static Set<Expression> expressions = new TreeSet<>();

    private static ClassLoader currentClassLoader;
    private static ResourceBundle currentResourceBundle;

    public static void register(JSONArray array, ClassLoader classLoader, ResourceBundle resourceBundle) {
        currentClassLoader = classLoader;
        currentResourceBundle = resourceBundle;
        for (Object obj : array) {
            if (obj instanceof JSONObject) {
                JSONObject json = (JSONObject) obj;
                if (json.has("method")) {
                    if (json.has("return")) {
                        register(new MethodExpression(json));
                    } else {
                        register(new MethodStatement(json));
                    }
                } else if (json.has("field")) {
                    register(new FieldExpression(json));
                } else if (json.has("class")) {
                    register(new ConstructorExpression(json));
                } else if (json.has("event")) {
                    CompEventListener.registerEvent(json);
                }
            }
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void register(String packageName, ClassLoader classLoader, ResourceBundle resourceBundle) {
        currentClassLoader = classLoader;
        currentResourceBundle = resourceBundle;
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(classLoader).getTopLevelClasses(packageName)) {
                Class<?> clazz = classInfo.load();
                if (BlockDefinition.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                    register((BlockDefinition<?>) clazz.getConstructor().newInstance());
                }
            }
        } catch (Exception e) {
            NotificationManager.displayException("Failed to register block", e);
        }
    }

    private static void register(BlockDefinition<?> definition) {
        if (definition instanceof PluginComponent) {
            pluginComponentMap.put(definition.getID(), (PluginComponent) definition);
            pluginComponents.add((PluginComponent) definition);
        } else if (definition instanceof Statement) {
            statementMap.put(definition.getID(), (Statement) definition);
            statements.add((Statement) definition);
        } else if (definition instanceof Expression) {
            expressionMap.put(definition.getID(), (Expression) definition);
            expressions.add((Expression) definition);
        }
    }

    public static Class<?> getClass(String className) {
        switch (className) {
            case "boolean": return boolean.class;
            case "byte": return byte.class;
            case "char": return char.class;
            case "double": return double.class;
            case "float": return float.class;
            case "int": return int.class;
            case "long": return long.class;
            case "short": return short.class;
            default:
                return classCache.computeIfAbsent(className, k -> {
                    try {
                        return Class.forName(k, false, currentClassLoader);
                    } catch (Exception e) {
                        throw new IllegalStateException();
                    }
                });
        }
    }

    public static PluginComponent getPluginComponent(String id) {
        return pluginComponentMap.get(id);
    }

    public static Statement getStatement(String id) {
        return statementMap.get(id);
    }

    public static Expression getExpression(String id) {
        return expressionMap.get(id);
    }

    public static Set<PluginComponent> getPluginComponents() {
        return Collections.unmodifiableSet(pluginComponents);
    }

    public static Set<Statement> getStatements() {
        return Collections.unmodifiableSet(statements);
    }

    public static Set<Expression> getExpressions() {
        return Collections.unmodifiableSet(expressions);
    }

    public static String getString(BlockDefinition<?> definition, String key, String def) {
        key = definition.getID() + "." + key;
        return currentResourceBundle != null && currentResourceBundle.containsKey(key) ? currentResourceBundle.getString(key) : def;
    }
}
