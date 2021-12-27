package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.generated.*;
import com.gmail.visualbukkit.extensions.DefaultBlocksExtension;
import com.gmail.visualbukkit.extensions.VisualBukkitExtension;
import com.gmail.visualbukkit.ui.NotificationManager;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.reflect.ClassPath;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.*;

public class BlockRegistry {

    private static ListMultimap<VisualBukkitExtension, BlockDefinition> allBlocks = ArrayListMultimap.create();
    private static Map<String, PluginComponent> activePluginComponents = new HashMap<>();
    private static Map<String, Statement> activeStatements = new HashMap<>();
    private static Map<String, Expression> activeExpressions = new HashMap<>();
    private static Set<VisualBukkitExtension> activeExtensions;

    public static void register(VisualBukkitExtension extension, JSONArray blockArray) {
        for (Object obj : blockArray) {
            if (obj instanceof JSONObject json) {
                if (json.has("method")) {
                    if (json.has("return")) {
                        allBlocks.put(extension, new MethodExpression(json));
                        String method = json.getString("method");
                        if (!method.matches("\\A(?:get|is|has|can)[A-Z].*") && !method.equals("values") && !method.equals("valueOf")) {
                            allBlocks.put(extension, new MethodStatement(json));
                        }
                    } else {
                        allBlocks.put(extension, new MethodStatement(json));
                    }
                } else if (json.has("field")) {
                    allBlocks.put(extension, new FieldExpression(json));
                } else if (json.has("event")) {
                    allBlocks.put(extension, new EventComponent(json));
                } else if (json.has("class")) {
                    allBlocks.put(extension, new ConstructorExpression(json));
                }
            }
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void register(VisualBukkitExtension extension, String packageName) {
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(extension.getClass().getClassLoader()).getTopLevelClasses(packageName)) {
                Class<?> clazz = classInfo.load();
                if (BlockDefinition.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                    allBlocks.put(extension, (BlockDefinition) clazz.getConstructor().newInstance());
                }
            }
        } catch (Exception e) {
            NotificationManager.displayException("Failed to register blocks", e);
        }
    }

    public static void setActiveExtensions(Collection<VisualBukkitExtension> extensions) {
        if (activeExtensions != null) {
            activeExtensions.forEach(VisualBukkitExtension::deactivate);
        }

        activeExtensions = new HashSet<>(extensions);
        activePluginComponents.clear();
        activeStatements.clear();
        activeExpressions.clear();

        activateExtension(DefaultBlocksExtension.getInstance());
        extensions.forEach(BlockRegistry::activateExtension);
        Set<BlockDefinition> activeBlocks = new TreeSet<>();
        activeBlocks.addAll(activePluginComponents.values());
        activeBlocks.addAll(activeStatements.values());
        activeBlocks.addAll(activeExpressions.values());
        VisualBukkitApp.getBlockSelector().setBlocks(activeBlocks);
    }

    private static void activateExtension(VisualBukkitExtension extension) {
        extension.activate();
        for (BlockDefinition block : allBlocks.get(extension)) {
            if (block instanceof PluginComponent p) {
                activePluginComponents.put(p.getID(), p);
            } else if (block instanceof Statement s) {
                activeStatements.put(s.getID(), s);
            } else if (block instanceof Expression e) {
                activeExpressions.put(e.getID(), e);
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    public static PluginComponent getPluginComponent(String id) {
        return activePluginComponents.get(id);
    }

    public static Statement getStatement(String id) {
        return activeStatements.get(id);
    }

    public static Expression getExpression(String id) {
        return activeExpressions.get(id);
    }
}
