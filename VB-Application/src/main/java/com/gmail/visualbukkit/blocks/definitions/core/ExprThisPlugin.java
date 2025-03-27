package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

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

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/plugin/java/JavaPlugin.html"));
    }
}
