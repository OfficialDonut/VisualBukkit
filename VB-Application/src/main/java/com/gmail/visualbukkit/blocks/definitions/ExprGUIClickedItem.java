package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprGUIClickedItem extends Expression {

    public ExprGUIClickedItem() {
        super("expr-gui-clicked-item", "Clicked Item", "GUI", "The clicked item");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.inventory.ItemStack");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent(CompGUIClickHandler.class);
            }

            @Override
            public String toJava() {
                return "event.getCurrentItem()";
            }
        };
    }
}
