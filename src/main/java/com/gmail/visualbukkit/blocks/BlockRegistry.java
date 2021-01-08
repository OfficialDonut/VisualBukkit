package com.gmail.visualbukkit.blocks;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.gui.NotificationManager;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

public class BlockRegistry {

    private static Map<String, StatementDefinition<?>> statements = new HashMap<>();
    private static Map<String, ExpressionDefinition<?>> expressions = new HashMap<>();

    public static void registerBlocks(String packageName) {
    	try(ScanResult sr = new ClassGraph().enableAllInfo().acceptPackages(packageName).scan()) {
    		sr.getSubclasses(CodeBlock.class.getName()).loadClasses().forEach(BlockRegistry::registerBlock);
    	}
    }

    @SuppressWarnings("unchecked")
	public static void registerBlock(Class<?> blockClass) {
        if (!Modifier.isAbstract(blockClass.getModifiers()) && !blockClass.isMemberClass()) {
            try {
                if (StatementBlock.class.isAssignableFrom(blockClass)) {
					StatementDefinition<?> statement = new StatementDefinition<>((Class<? extends StatementBlock>) blockClass);
                    statements.put(blockClass.getName(), statement);
                    VisualBukkit.getInstance().getBlockSelector().add(statement);
                } else if (ExpressionBlock.class.isAssignableFrom(blockClass)) {
                    expressions.put(blockClass.getName(), new ExpressionDefinition<>((Class<? extends ExpressionBlock>) blockClass));
                }
            } catch (NoSuchMethodException e) {
                NotificationManager.displayException("Failed to register block", e);
            }
        }
    }

    public static String getIdentifier(BlockDefinition<?> def) {
        return def.getBlockClass().getName();
    }

    public static String getIdentifier(CodeBlock block) {
        return block.getClass().getName();
    }

    public static StatementDefinition<?> getStatement(String identifier) {
        return statements.get(identifier);
    }

    public static ExpressionDefinition<?> getExpression(String identifier) {
        return expressions.get(identifier);
    }

    public static StatementDefinition<?> getStatement(StatementBlock block) {
        return statements.get(getIdentifier(block));
    }

    public static ExpressionDefinition<?> getExpression(ExpressionBlock<?> block) {
        return expressions.get(getIdentifier(block));
    }

    public static Collection<StatementDefinition<?>> getStatements() {
        return Collections.unmodifiableCollection(statements.values());
    }

    public static Collection<ExpressionDefinition<?>> getExpressions() {
        return Collections.unmodifiableCollection(expressions.values());
    }
}
