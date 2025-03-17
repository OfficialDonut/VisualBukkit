package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.blocks.definitions.core.CompEventListener;
import com.gmail.visualbukkit.blocks.definitions.core.ExprField;
import com.gmail.visualbukkit.blocks.definitions.core.ExprMethod;
import com.gmail.visualbukkit.blocks.definitions.core.StatMethod;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class JavadocsManager {

    public static void getStatJavadocs(StatMethod statMethod) {
        getJavadocs(statMethod.getClassParameter().getValue().getName(), statMethod.getMethodParameter().getValue().getSignature());
    }

    public static void getExprJavadocs(ExprMethod exprMethod) {
        getJavadocs(exprMethod.getClassParameter().getValue().getName(), exprMethod.getMethodParameter().getValue().getSignature());
    }

    public static void getExprJavadocs(ExprField exprField) {
        getJavadocs(exprField.getClassParameter().getValue().getName(), exprField.getFieldParameter().getValue().getName());
    }

    public static void getCompJavaDocs(PluginComponent pluginComponent) {
        switch (pluginComponent.getBlockType().orElse("Unknown")) {
            case "comp-command":
                getJavadocs("org.bukkit.command.Command", "");
                break;
            case "comp-event-listener":
                CompEventListener compEventListener = (CompEventListener) pluginComponent.getBlock().orElseGet(CompEventListener::new);
                getJavadocs((compEventListener.getEvent().getPackage() + "/" + compEventListener.getEvent().toString().split("\\(")[0]).strip(), "");
                break;
            case "comp-plugin-enable":
                getJavadocs("org.bukkit.plugin.java.JavaPlugin", "onEnable()");
                break;
            case "comp-plugin-disable":
                getJavadocs("org.bukkit.plugin.java.JavaPlugin", "onDisable()");
                break;
            case "comp-tab-complete-handler":
                getJavadocs("org.bukkit.command.TabCompleter", "onTabComplete()");
                break;
            case "comp-gui":
                getJavadocs("org.bukkit.inventory.Inventory", "");
                break;
            case "comp-gui-click-handler":
                getJavadocs("org.bukkit.event.inventory.InventoryClickEvent", "");
                break;
            case "comp-function":
                getJavadocs("java.util.function.Function", "");
                break;
            case "comp-procedure":
                getJavadocs("java.util.function.Supplier", "");
                break;
            case "comp-consumer":
                getJavadocs("java.util.function.Consumer", "");
                break;

            default:
                VisualBukkitApp.getLogger().warning("No Javadocs available for this component: " + pluginComponent.getBlockType().orElse("Unknown"));
        }
    }


    private static void getJavadocs(String className, String methodName) {
        for (List<String> packages : Project.getJavadocsMap().values()) {
            for (String packageName : packages) {
                if (className.startsWith(packageName)) {
                    String baseUrl = getKeyByValue(Project.getJavadocsMap(), packageName);
                    String url = baseUrl + className.replaceAll("\\.", "/") + ".html#" + methodName;
                    try {
                        Desktop.getDesktop().browse(java.net.URI.create(url));
                    } catch (java.io.IOException e) {
                        VisualBukkitApp.getLogger().warning("Please ensure you have a browser installed on your system.");
                    }
                }
            }
        }
    }

    private static String getKeyByValue(HashMap<String, List<String>> map, String value) {
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getValue().contains(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

}
