package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.stdlib.*;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public abstract class PluginModule {

    public abstract void prepareBuild(BuildContext buildContext);

    public static final PluginModule DATABASE = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext context) {
            context.addUtilClasses(DatabaseManager.class);
            context.getMainClass().addImport("com.gmail.visualbukkit.stdlib.DatabaseManager");
            context.getMainClass().addImport("java.sql.*");
            context.addMavenDependencies(
                    "<groupId>com.zaxxer</groupId>\n" +
                    "<artifactId>HikariCP</artifactId>\n" +
                    "<version>3.4.5</version>\n");
        }
    };

    public static final PluginModule GUI = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext context) {
            context.addUtilClasses(GUIClickEvent.class, GUIIdentifier.class, GUIManager.class);
            context.getMainClass().addImport("com.gmail.visualbukkit.stdlib.*");
            MethodSource<JavaClassSource> enableMethod = context.getMainClass().getMethod("onEnable");
            enableMethod.setBody(enableMethod.getBody() + "getServer().getPluginManager().registerEvents(GUIManager.getInstance(), this);");
        }
    };

    public static final PluginModule REFLECTION_UTIL = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext context) {
            context.addUtilClasses(ReflectionUtil.class);
            context.getMainClass().addImport("com.gmail.visualbukkit.stdlib.ReflectionUtil");
        }
    };

    public static final PluginModule VARIABLES = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext context) {
            context.addUtilClasses(VariableManager.class);
            context.getMainClass().addImport("com.gmail.visualbukkit.stdlib.VariableManager");
            MethodSource<JavaClassSource> enableMethod = context.getMainClass().getMethod("onEnable");
            MethodSource<JavaClassSource> disableMethod = context.getMainClass().getMethod("onDisable");
            enableMethod.setBody("VariableManager.loadVariables(this);" + enableMethod.getBody());
            disableMethod.setBody(disableMethod.getBody() + "VariableManager.saveVariables();");
        }
    };
}
