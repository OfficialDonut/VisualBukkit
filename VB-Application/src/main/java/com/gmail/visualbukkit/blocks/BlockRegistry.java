package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.definitions.CompEventListener;
import com.gmail.visualbukkit.extensions.DefaultBlocksExtension;
import com.gmail.visualbukkit.extensions.VisualBukkitExtension;
import com.gmail.visualbukkit.project.Project;
import com.gmail.visualbukkit.ui.NotificationManager;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.reflect.ClassPath;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.*;

public class BlockRegistry {

    private final static SetMultimap<VisualBukkitExtension, BlockDefinition> definitionMap = HashMultimap.create();
    private final static SetMultimap<VisualBukkitExtension, JSONObject> eventMap = HashMultimap.create();
    private final static Map<String, PluginComponent> pluginComponentMap = new HashMap<>();
    private final static Map<String, Statement> statementMap = new HashMap<>();
    private final static Map<String, Expression> expressionMap = new HashMap<>();

    private static ResourceBundle currentResourceBundle;
    private static Set<VisualBukkitExtension> activeExtensions;

    public static void register(VisualBukkitExtension extension, JSONArray blockArray, ResourceBundle resourceBundle) {
        currentResourceBundle = resourceBundle;
        for (Object obj : blockArray) {
            if (obj instanceof JSONObject) {
                JSONObject json = (JSONObject) obj;
                if (json.has("method")) {
                    if (json.has("return")) {
                        definitionMap.put(extension, new MethodExpression(json));
                        String method = json.getString("method");
                        if (!method.matches("\\A(?:get|is|has)[A-Z].*") && !method.equals("values") && !method.equals("valueOf")) {
                            definitionMap.put(extension, new MethodStatement(json));
                        }
                    } else {
                        definitionMap.put(extension, new MethodStatement(json));
                    }
                } else if (json.has("field")) {
                    definitionMap.put(extension, new FieldExpression(json));
                } else if (json.has("class")) {
                    definitionMap.put(extension, new ConstructorExpression(json));
                } else if (json.has("event")) {
                    eventMap.put(extension, json);
                }
            }
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void register(VisualBukkitExtension extension, String packageName, ResourceBundle resourceBundle) {
        currentResourceBundle = resourceBundle;
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(extension.getClass().getClassLoader()).getTopLevelClasses(packageName)) {
                Class<?> clazz = classInfo.load();
                if (BlockDefinition.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                    definitionMap.put(extension, (BlockDefinition) clazz.getConstructor().newInstance());
                }
            }
        } catch (Exception e) {
            NotificationManager.displayException("Failed to register blocks", e);
        }
    }

    public static void setActiveExtensions(Collection<VisualBukkitExtension> extensions) {
        if (activeExtensions != null) {
            for (VisualBukkitExtension extension : activeExtensions) {
                extension.deactivate();
            }
        }

        activeExtensions = new HashSet<>(extensions);
        pluginComponentMap.clear();
        statementMap.clear();
        expressionMap.clear();
        CompEventListener.clearEvents();

        activateExtension(DefaultBlocksExtension.getInstance());
        for (VisualBukkitExtension extension : extensions) {
            activateExtension(extension);
        }

        Project.AVAILABLE_PLUGIN_COMPONENTS = new TreeSet<>(pluginComponentMap.values());
        VisualBukkitApp.getStatementSelector().setStatements(new TreeSet<>(statementMap.values()));
        VisualBukkitApp.getExpressionSelector().setExpressions(new TreeSet<>(expressionMap.values()));
    }

    private static void activateExtension(VisualBukkitExtension extension) {
        extension.activate();
        for (BlockDefinition definition : definitionMap.get(extension)) {
            if (definition instanceof PluginComponent) {
                pluginComponentMap.put(definition.getID(), (PluginComponent) definition);
            } else if (definition instanceof Statement) {
                statementMap.put(definition.getID(), (Statement) definition);
            } else if (definition instanceof Expression) {
                expressionMap.put(definition.getID(), (Expression) definition);
            } else {
                throw new IllegalStateException();
            }
        }
        for (JSONObject json : eventMap.get(extension)) {
            CompEventListener.addEvent(json);
        }
    }

    public static String getString(BlockDefinition definition, String key, String def) {
        key = definition.getID() + "." + key;
        return currentResourceBundle != null && currentResourceBundle.containsKey(key) ? currentResourceBundle.getString(key) : def;
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
}
