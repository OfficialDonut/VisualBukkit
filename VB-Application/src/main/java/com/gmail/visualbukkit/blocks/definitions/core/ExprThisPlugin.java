package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-this-plugin", name = "This Plugin", description = "A reference to this plugin")
public class ExprThisPlugin extends ExpressionBlock {

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.plugin.java.JavaPlugin");
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "PluginMain.getInstance()";
    }
}
