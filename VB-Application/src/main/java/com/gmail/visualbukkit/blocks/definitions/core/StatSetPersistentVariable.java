package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "stat-set-persistent-variable", name = "Set Persistent Variable", description = "Sets the value of a persistent variable")
public class StatSetPersistentVariable extends StatementBlock {

    public StatSetPersistentVariable() {
        addParameter("Variable", new ExpressionParameter(ClassInfo.of(String.class)));
        addParameter("Value", new ExpressionParameter(ClassInfo.of(Object.class)));
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/wiki/Variables"));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        ExprPersistentVariable.prepareClass(buildInfo.getMainClass());
        return "PluginMain.persistentData.set(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + ");";
    }
}
