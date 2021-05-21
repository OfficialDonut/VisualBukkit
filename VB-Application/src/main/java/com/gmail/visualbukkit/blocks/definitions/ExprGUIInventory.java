package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprGUIInventory extends Expression {

    public ExprGUIInventory() {
        super("expr-gui-inventory");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.inventory.Inventory");
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
