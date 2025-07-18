package com.gmail.visualbukkit.blocks.definitions.gui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "expr-gui-clicked-inventory", name = "GUI Clicked Inventory", description = "Must be used in a 'GUI Click Handler' plugin component")
public class ExprGUIClickedInventory extends ExpressionBlock {

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompGUIClickHandler.class);
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/event/inventory/InventoryClickEvent.html#getClickedInventory()"));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.inventory.Inventory");
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "guiClickEvent.getInventoryClickEvent().getClickedInventory()";
    }
}
