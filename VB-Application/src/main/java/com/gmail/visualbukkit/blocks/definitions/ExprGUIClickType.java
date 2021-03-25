package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import org.bukkit.event.inventory.ClickType;

public class ExprGUIClickType extends Expression {

    public ExprGUIClickType() {
        super("expr-gui-click-type", ClickType.class);
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
                return "event.getClick()";
            }
        };
    }
}
