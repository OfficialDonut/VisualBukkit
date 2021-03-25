package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import org.bukkit.event.inventory.InventoryAction;

public class ExprGUIClickAction extends Expression {

    public ExprGUIClickAction() {
        super("expr-gui-click-action", InventoryAction.class);
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
