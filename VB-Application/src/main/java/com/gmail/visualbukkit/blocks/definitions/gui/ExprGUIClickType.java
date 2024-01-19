package com.gmail.visualbukkit.blocks.definitions.gui;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-gui-click-type", name = "GUI Click Type", description = "Must be used in a 'GUI Click Handler' plugin component")
public class ExprGUIClickType extends ExpressionBlock {

    @Override
    public void updateState() {
        super.updateState();
        if (!(getPluginComponentBlock() instanceof CompGUIClickHandler)) {
            pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        }
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.event.inventory.ClickType");
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "guiClickEvent.getInventoryClickEvent().getClick()";
    }
}
