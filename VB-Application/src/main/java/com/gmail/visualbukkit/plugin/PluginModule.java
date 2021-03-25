package com.gmail.visualbukkit.plugin;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.HashMap;
import java.util.Map;

public abstract class PluginModule {

    public abstract void prepareBuild(BuildContext buildContext);

    private static Map<String, PluginModule> pluginModules = new HashMap<>();

    public static void register(String moduleID, PluginModule module) {
        pluginModules.put(moduleID, module);
    }

    public static PluginModule get(String moduleID) {
        return pluginModules.get(moduleID);
    }

    public static final PluginModule GUI = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext buildContext) {
            buildContext.addUtilClass(PluginBuilder.getUtilClass("GUIClickEvent"));
            buildContext.addUtilClass(PluginBuilder.getUtilClass("GUIIdentifier"));
            buildContext.addUtilClass(PluginBuilder.getUtilClass("GUIManager"));
            buildContext.getMainClass().addMethod("@EventHandler\npublic void onGUIClick(GUIClickEvent event) throws Exception {}");
            MethodSource<JavaClassSource> enableMethod = buildContext.getMainClass().getMethod("onEnable");
            enableMethod.setBody(enableMethod.getBody() + "getServer().getPluginManager().registerEvents(GUIManager.getInstance(), this);");
        }
    };

    public static final PluginModule VARIABLES = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext buildContext) {
            buildContext.addUtilClass(PluginBuilder.getUtilClass("VariableManager"));
            MethodSource<JavaClassSource> enableMethod = buildContext.getMainClass().getMethod("onEnable");
            MethodSource<JavaClassSource> disableMethod = buildContext.getMainClass().getMethod("onDisable");
            enableMethod.setBody("VariableManager.loadVariables(this);" + enableMethod.getBody());
            disableMethod.setBody(disableMethod.getBody() + "VariableManager.saveVariables();");
        }
    };
}
