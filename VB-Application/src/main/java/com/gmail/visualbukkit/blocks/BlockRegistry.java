package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.google.common.reflect.ClassPath;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class BlockRegistry {

    private static final Map<String, BlockFactory<PluginComponentBlock>> pluginComponents = new HashMap<>();
    private static final Map<String, BlockFactory<StatementBlock>> statements = new HashMap<>();
    private static final Map<String, BlockFactory<ExpressionBlock>> expressions = new HashMap<>();

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
            pluginComponents.put(definition.id(), new BlockFactory<>(clazz));
        } else if (StatementBlock.class.isAssignableFrom(clazz)) {
            statements.put(definition.id(), new BlockFactory<>(clazz));
        } else if (ExpressionBlock.class.isAssignableFrom(clazz)) {
            expressions.put(definition.id(), new BlockFactory<>(clazz));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static void clear() {
        pluginComponents.clear();
        statements.clear();
        expressions.clear();
    }

    public static PluginComponentBlock newPluginComponent(JSONObject json) {
        return getPluginComponent(json.optString("type")).newBlock(json);
    }

    public static StatementBlock newStatement(JSONObject json) {
        return getStatement(json.optString("type")).newBlock(json);
    }

    public static ExpressionBlock newExpression(JSONObject json) {
        return getExpression(json.optString("type")).newBlock(json);
    }

    public static BlockFactory<PluginComponentBlock> getPluginComponent(String id) {
        return pluginComponents.computeIfAbsent(id, k -> new BlockFactory<>(PluginComponentBlock.Unknown.class));
    }

    public static BlockFactory<StatementBlock> getStatement(String id) {
        return statements.computeIfAbsent(id, k -> new BlockFactory<>(StatementBlock.Unknown.class));
    }

    public static BlockFactory<ExpressionBlock> getExpression(String id) {
        return expressions.computeIfAbsent(id, k -> new BlockFactory<>(ExpressionBlock.Unknown.class));
    }

    public static Set<BlockFactory<PluginComponentBlock>> getPluginComponents() {
        return new TreeSet<>(pluginComponents.values());
    }

    public static Set<BlockFactory<StatementBlock>> getStatements() {
        return new TreeSet<>(statements.values());
    }

    public static Set<BlockFactory<ExpressionBlock>> getExpressions() {
        return new TreeSet<>(expressions.values());
    }
}
