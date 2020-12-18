package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.stdlib.VariableManager;
import com.gmail.visualbukkit.stdlib.VariableType;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public abstract class PluginModule {

    public abstract void prepareBuild(BuildContext buildContext);

    public static final PluginModule VARIABLES = new PluginModule() {
        @Override
        public void prepareBuild(BuildContext context) {
            context.getMainClass().addImport("com.gmail.visualbukkit.stdlib.*");
            context.addUtilClasses(VariableManager.class, VariableType.class);
            MethodSource<JavaClassSource> enableMethod = context.getMainClass().getMethod("onEnable");
            MethodSource<JavaClassSource> disableMethod = context.getMainClass().getMethod("onDisable");
            enableMethod.setBody("VariableManager.loadVariables(this);" + enableMethod.getBody());
            disableMethod.setBody(disableMethod.getBody() + "VariableManager.saveVariables();");
        }
    };
}
