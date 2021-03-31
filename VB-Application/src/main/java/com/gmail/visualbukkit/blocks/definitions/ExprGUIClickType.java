package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprGUIClickType extends Expression {

    public ExprGUIClickType() {
        super("expr-gui-click-type", ClassInfo.of("org.bukkit.event.inventory.ClickType"));
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
