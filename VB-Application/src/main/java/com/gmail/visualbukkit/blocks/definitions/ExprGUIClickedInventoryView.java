package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprGUIClickedInventoryView extends Expression {

    public ExprGUIClickedInventoryView() {
        super("expr-gui-clicked-inventory-view", ClassInfo.of("org.bukkit.inventory.InventoryView"));
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
                return "event.getView()";
            }
        };
    }
}
