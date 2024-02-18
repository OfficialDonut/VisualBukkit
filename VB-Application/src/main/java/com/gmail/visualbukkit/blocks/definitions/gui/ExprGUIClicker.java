package com.gmail.visualbukkit.blocks.definitions.gui;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-gui-clicker", name = "GUI Clicker", description = "Must be used in a 'GUI Click Handler' plugin component")
public class ExprGUIClicker extends ExpressionBlock {

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompGUIClickHandler.class);
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.entity.Player");
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "((org.bukkit.entity.Player) guiClickEvent.getInventoryClickEvent().getWhoClicked())";
    }
}
