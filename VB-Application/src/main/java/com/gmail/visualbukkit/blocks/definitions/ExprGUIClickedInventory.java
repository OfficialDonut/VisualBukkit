package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import org.bukkit.inventory.Inventory;

public class ExprGUIClickedInventory extends Expression {

    public ExprGUIClickedInventory() {
        super("expr-gui-clicked-inventory", Inventory.class);
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
                return "event.getClickedInventory()";
            }
        };
    }
}
