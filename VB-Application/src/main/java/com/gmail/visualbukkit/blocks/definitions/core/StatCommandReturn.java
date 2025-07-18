package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "stat-command-return", name = "Command Return", description = "Terminates a command and indicates whether it was successful (must be used in a 'Command' plugin component")
public class StatCommandReturn extends StatementBlock {

    public StatCommandReturn() {
        addParameter("Value", new ExpressionParameter(ClassInfo.of(boolean.class)));
    }

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompCommand.class);
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/command/CommandSender.html#sendMessage(java.lang.String)"));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "if (true) return " + arg(0, buildInfo) + ";";
    }
}
