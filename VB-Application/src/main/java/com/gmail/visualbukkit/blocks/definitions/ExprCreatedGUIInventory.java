package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprCreatedGUIInventory extends Expression {

    public ExprCreatedGUIInventory() {
        super("expr-created-gui-inventory", "Created GUI Inventory", "GUI", "The inventory created for the GUI");
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
                checkForPluginComponent(CompCreateGUI.class);
            }

            @Override
            public String toJava() {
                return "guiInventory";
            }
        };
    }
}
