package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.project.BuildInfo;

import java.net.URI;

@BlockDefinition(id = "stat-break-loop", name = "Break Loop", description = "Immediately stops a loop")
public class StatBreakLoop extends StatementBlock {

    @Override
    public void updateState() {
        super.updateState();
        checkForContainer(StatWhileLoop.class, StatListLoop.class, StatNumberLoop.class, StatAdvancedNumberLoop.class);
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/branch.html"));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "if (true) break;";
    }
}
