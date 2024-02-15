package com.gmail.visualbukkit.blocks.definitions.gui;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-gui-inventory", name = "GUI Inventory", description = "Must be used in a 'GUI' plugin component")
public class ExprGUIInventory extends ExpressionBlock {

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompGUI.class);
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.inventory.Inventory");
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "guiInventory";
    }
}
