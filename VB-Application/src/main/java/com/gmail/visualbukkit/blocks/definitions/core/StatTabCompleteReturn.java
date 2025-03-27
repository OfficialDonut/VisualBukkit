package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;
import java.util.List;

@BlockDefinition(id = "stat-tab-complete-return", name = "Tab Complete Return", description = "Returns a list of completions for a command argument (must be used in a 'Tab Complete Handler' plugin component)")
public class StatTabCompleteReturn extends StatementBlock {

    public StatTabCompleteReturn() {
        addParameter("Completions", new ExpressionParameter(ClassInfo.of(List.class)));
    }

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompTabCompleteHandler.class);
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/command/TabCompleter.html#onTabComplete(org.bukkit.command.CommandSender,org.bukkit.command.Command,java.lang.String,java.lang.String[])"));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "if (true) return " + arg(0, buildInfo) + ";";
    }
}
