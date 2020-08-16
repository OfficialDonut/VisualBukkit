package us.donut.visualbukkit.plugin;

import us.donut.visualbukkit.plugin.modules.PluginModule;

import java.util.HashSet;
import java.util.Set;

public class BuildContext {

    private static Set<PluginModule> pluginModules;
    private static Set<String> utilMethods;
    private static Set<String> localVariables;

    public static void create() {
        pluginModules = new HashSet<>();
        utilMethods = new HashSet<>();
        localVariables = new HashSet<>();
    }

    public static String declareLocalVariables() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String variable : localVariables) {
            stringBuilder.append("Object ").append(variable).append("= null;");
        }
        localVariables.clear();
        return stringBuilder.toString();
    }

    public static void addPluginModule(PluginModule pluginModule) {
        pluginModules.add(pluginModule);
    }

    public static void addUtilMethod(UtilMethod method) {
        utilMethods.add(method.getMethodName());
    }

    public static void addLocalVariable(String variableName) {
        localVariables.add(variableName);
    }

    public static Set<PluginModule> getPluginModules() {
        return pluginModules;
    }

    public static Set<String> getUtilMethods() {
        return utilMethods;
    }

    public static Set<String> getLocalVariables() {
        return localVariables;
    }
}
