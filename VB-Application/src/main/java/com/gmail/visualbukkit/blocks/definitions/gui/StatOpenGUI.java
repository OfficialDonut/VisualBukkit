package com.gmail.visualbukkit.blocks.definitions.gui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.PluginComponentParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "stat-open-gui", name = "Open GUI", description = "Opens a GUI to a player")
public class StatOpenGUI extends StatementBlock {

    public StatOpenGUI() {
        addParameter("GUI", new PluginComponentParameter(CompGUI.class));
        addParameter("Player", new ExpressionParameter(ClassInfo.of("org.bukkit.entity.Player")));
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/entity/HumanEntity.html#openInventory(org.bukkit.inventory.Inventory)"));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "GUIManager.getInstance().open(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + ");";
    }
}
