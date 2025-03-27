package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.project.BuildInfo;

import java.net.URI;

@BlockDefinition(id = "stat-continue-loop", name = "Continue Loop", description = "Advances a loop to the next iteration")
public class StatContinueLoop extends StatementBlock {

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
        return "if (true) continue;";
    }
}
