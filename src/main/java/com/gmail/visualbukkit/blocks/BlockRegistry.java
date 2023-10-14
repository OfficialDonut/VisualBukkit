package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.google.common.reflect.ClassPath;

import java.lang.reflect.Modifier;
import java.util.*;

public class BlockRegistry {

    private static final Map<String, PluginComponentBlock.Factory> pluginComponents = new HashMap<>();
    private static final Map<String, StatementBlock.Factory> statements = new HashMap<>();
    private static final Map<String, ExpressionBlock.Factory> expressions = new HashMap<>();

    public static void register(ClassLoader classLoader, String packageName) {
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(classLoader).getTopLevelClasses(packageName)) {
                Class<?> clazz = classInfo.load();
                if (clazz.isAnnotationPresent(BlockDefinition.class) && !Modifier.isAbstract(clazz.getModifiers())) {
                    register(clazz);
                }
            }
        } catch (Exception e) {
            VisualBukkitApp.displayException(e);
        }
    }

    public static void register(Class<?> clazz) {
        BlockDefinition definition = clazz.getAnnotation(BlockDefinition.class);
        if (PluginComponentBlock.class.isAssignableFrom(clazz)) {
            pluginComponents.put(definition.uid(), new PluginComponentBlock.Factory(clazz));
        } else if (StatementBlock.class.isAssignableFrom(clazz)) {
            statements.put(definition.uid(), new StatementBlock.Factory(clazz));
        } else if (ExpressionBlock.class.isAssignableFrom(clazz)) {
            expressions.put(definition.uid(), new ExpressionBlock.Factory(clazz));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static void clear() {
        pluginComponents.clear();
        statements.clear();
        expressions.clear();
    }

    public static PluginComponentBlock.Factory getPluginComponent(String id) {
        return pluginComponents.computeIfAbsent(id, k -> PluginComponentBlock.Unknown.factory);
    }

    public static StatementBlock.Factory getStatement(String id) {
        return statements.computeIfAbsent(id, k -> StatementBlock.Unknown.factory);
    }

    public static ExpressionBlock.Factory getExpression(String id) {
        return expressions.computeIfAbsent(id, k -> ExpressionBlock.Unknown.factory);
    }

    public static Set<PluginComponentBlock.Factory> getPluginComponents() {
        return new TreeSet<>(pluginComponents.values());
    }

    public static Set<StatementBlock.Factory> getStatements() {
        return new TreeSet<>(statements.values());
    }

    public static Set<ExpressionBlock.Factory> getExpressions() {
        return new TreeSet<>(expressions.values());
    }
}
