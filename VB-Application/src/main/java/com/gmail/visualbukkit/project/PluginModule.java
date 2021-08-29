package com.gmail.visualbukkit.project;

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

    public static final PluginModule BUNGEE = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext buildContext) {
            MethodSource<JavaClassSource> enableMethod = buildContext.getMainClass().getMethod("onEnable");
            MethodSource<JavaClassSource> disableMethod = buildContext.getMainClass().getMethod("onDisable");
            enableMethod.setBody(enableMethod.getBody() + "Bukkit.getMessenger().registerOutgoingPluginChannel(this, \"BungeeCord\");");
            disableMethod.setBody(disableMethod.getBody() + "Bukkit.getMessenger().unregisterOutgoingPluginChannel(this);");
        }
    };

    public static final PluginModule DATABASE = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext context) {
            context.addUtilClass(PluginBuilder.getUtilClass("DatabaseManager"));
            context.getMainClass().addImport("java.sql.*");
            context.addMavenDependency(
                    "<groupId>com.zaxxer</groupId>\n" +
                    "<artifactId>HikariCP</artifactId>\n" +
                    "<version>3.4.5</version>\n");
        }
    };

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

    public static final PluginModule PERSISTENT_VARIABLES = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext buildContext) {
            buildContext.getMainClass().addField("public static org.bukkit.configuration.file.YamlConfiguration PERSISTENT_VARIABLES;");
            MethodSource<JavaClassSource> enableMethod = buildContext.getMainClass().getMethod("onEnable");
            MethodSource<JavaClassSource> disableMethod = buildContext.getMainClass().getMethod("onDisable");
            enableMethod.setBody(
                    enableMethod.getBody() +
                    "PERSISTENT_VARIABLES = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(new File(getDataFolder(), \"data.yml\"));");
            disableMethod.setBody(disableMethod.getBody() + "try { PERSISTENT_VARIABLES.save(new File(getDataFolder(), \"data.yml\")); } catch (IOException e) { e.printStackTrace(); }");
        }
    };

    public static final PluginModule PLAYER_DATA = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext buildContext) {
            buildContext.addUtilClass(PluginBuilder.getUtilClass("PlayerDataManager"));
            MethodSource<JavaClassSource> enableMethod = buildContext.getMainClass().getMethod("onEnable");
            MethodSource<JavaClassSource> disableMethod = buildContext.getMainClass().getMethod("onDisable");
            enableMethod.setBody(enableMethod.getBody() + "getServer().getPluginManager().registerEvents(PlayerDataManager.getInstance(), this);");
            disableMethod.setBody(disableMethod.getBody() + "PlayerDataManager.getInstance().saveAllData();");
        }
    };

    public static final PluginModule REFLECTION_UTIL = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext buildContext) {
            buildContext.getUtilClasses().add(PluginBuilder.getUtilClass("ReflectionUtil"));
        }
    };
}
