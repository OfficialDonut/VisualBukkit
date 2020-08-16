package us.donut.visualbukkit.blocks;

import org.reflections.Reflections;
import us.donut.visualbukkit.VisualBukkit;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BlockRegistry {

    private static Map<String, StatementDefinition<?>> statements = new HashMap<>();
    private static Map<String, ExpressionDefinition<?>> expressions = new HashMap<>();

    public static void registerAll() {
        Reflections reflections = new Reflections("us.donut.visualbukkit.blocks");
        for (Class<? extends StatementBlock> clazz : reflections.getSubTypesOf(StatementBlock.class)) {
            if (!Modifier.isAbstract(clazz.getModifiers()) && !clazz.isMemberClass()) {
                try {
                    statements.put(clazz.getName(), new StatementDefinition<>(clazz));
                } catch (NoSuchMethodException e) {
                    VisualBukkit.displayException("Failed to load statement", e);
                }
            }
        }
        for (Class<? extends ExpressionBlock> clazz : reflections.getSubTypesOf(ExpressionBlock.class)) {
            if (!Modifier.isAbstract(clazz.getModifiers()) && !clazz.isMemberClass()) {
                try {
                    expressions.put(clazz.getName(), new ExpressionDefinition<>(clazz));
                } catch (NoSuchMethodException e) {
                    VisualBukkit.displayException("Failed to load expression", e);
                }
            }
        }
    }

    public static StatementDefinition<?> getStatement(String className) {
        if (className != null && !className.startsWith("us.donut.visualbukkit.blocks.")) {
            className = "us.donut.visualbukkit.blocks." + className;
        }
        return statements.get(className);
    }

    public static ExpressionDefinition<?> getExpression(String className) {
        if (className != null && !className.startsWith("us.donut.visualbukkit.blocks.")) {
            className = "us.donut.visualbukkit.blocks." + className;
        }
        return expressions.get(className);
    }

    public static StatementDefinition<?> getStatement(Class<? extends StatementBlock> clazz) {
        return getStatement(clazz.getName());
    }

    public static ExpressionDefinition<?> getExpression(Class<? extends ExpressionBlock> clazz) {
        return getExpression(clazz.getName());
    }

    public static Collection<StatementDefinition<?>> getStatements() {
        return Collections.unmodifiableCollection(statements.values());
    }

    public static Collection<ExpressionDefinition<?>> getExpressions() {
        return Collections.unmodifiableCollection(expressions.values());
    }
}
