package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import org.bukkit.inventory.Inventory;

public class ExprGUIInventory extends Expression {

    public ExprGUIInventory() {
        super("expr-gui-inventory", Inventory.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-create-gui");
            }

            @Override
            public String toJava() {
                return "guiInventory";
            }
        };
    }
}
