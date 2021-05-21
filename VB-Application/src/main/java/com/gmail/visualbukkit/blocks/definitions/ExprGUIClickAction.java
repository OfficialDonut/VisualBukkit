package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprGUIClickAction extends Expression {

    public ExprGUIClickAction() {
        super("expr-gui-click-action");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.event.inventory.InventoryAction");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-gui-click-handler");
            }

            @Override
            public String toJava() {
                return "event.getAction()";
            }
        };
    }
}
