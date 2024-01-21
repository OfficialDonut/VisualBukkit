package com.gmail.visualbukkit.blocks.definitions.gui;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "stat-set-gui-slot", name = "Set GUI Slot", description = "Must be used in a 'GUI' plugin component")
public class StatSetGUISlot extends StatementBlock {

    public StatSetGUISlot() {
        addParameter("Slot", new ExpressionParameter(ClassInfo.of(int.class)));
        addParameter("ItemStack", new ExpressionParameter(ClassInfo.of("org.bukkit.inventory.ItemStack")));
    }

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompGUI.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "guiInventory.setItem(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + ");";
    }
}
