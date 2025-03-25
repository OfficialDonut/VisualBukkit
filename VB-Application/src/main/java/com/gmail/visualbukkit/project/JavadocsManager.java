package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.definitions.core.*;
import org.json.JSONObject;

import java.net.URI;
import java.util.List;
import java.util.Map;

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
        JSONObject remapData = Project.getRemapData();
        if (remapData != null) {
            String key = className + "#" + methodName;
            if (remapData.has(key)) {
                String remapped = remapData.getString(key);
                String[] parts = remapped.split("#");
                if (parts.length == 2) {
                    className = parts[0];
                    methodName = parts[1];
                } else {
                    methodName = remapped;
                }
            }
        }

        for (Map.Entry<String, List<String>> entry : Project.getJavadocsMap().entrySet()) {
            String baseUrl = entry.getKey();
            List<String> packages = entry.getValue();

            for (String packageName : packages) {
                if (className.startsWith(packageName)) {
                    String url = baseUrl + className.replaceAll("\\.", "/") + ".html#" + methodName;
                    VisualBukkitApp.openURI(URI.create(url));
                    return;
                }
            }
        }
    }

}
