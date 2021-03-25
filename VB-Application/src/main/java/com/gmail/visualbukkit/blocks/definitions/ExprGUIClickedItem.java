package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import org.bukkit.inventory.ItemStack;

public class ExprGUIClickedItem extends Expression {

    public ExprGUIClickedItem() {
        super("expr-gui-clicked-item", ItemStack.class);
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
                return "event.getCurrentItem()";
            }
        };
    }
}
