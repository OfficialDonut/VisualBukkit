package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

@BlockDefinition(id = "expr-persistent-variable", name = "Persistent Variable", description = "The value of a persistent variable")
public class ExprPersistentVariable extends ExpressionBlock {

    public ExprPersistentVariable() {
        addParameter("Variable", new ExpressionParameter(ClassInfo.of(String.class)));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        prepareClass(buildInfo.getMainClass());
        return "PluginMain.persistentData.get(" + arg(0, buildInfo) + ")";
    }

    protected static void prepareClass(JavaClassSource clazz) {
        if (!clazz.hasField("persistentData")) {
            clazz.addImport("org.bukkit.configuration.file.YamlConfiguration");
            clazz.addField("public static YamlConfiguration persistentData = new YamlConfiguration();");
            MethodSource<JavaClassSource> enableMethod = clazz.getMethod("onEnable");
            enableMethod.setBody("persistentData = YamlConfiguration.loadConfiguration(new File(getDataFolder(), \"data.yml\"));" + enableMethod.getBody());
            MethodSource<JavaClassSource> disableMethod = clazz.getMethod("onDisable");
            disableMethod.setBody(disableMethod.getBody() + "try { persistentData.save(new File(getDataFolder(), \"data.yml\")); } catch (IOException e) { e.printStackTrace(); }");
        }
    }
}
