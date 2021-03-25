package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;

public class ExprGUIClickedSlot extends Expression {

    public ExprGUIClickedSlot() {
        super("expr-gui-clicked-slot", int.class);
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
                return "event.getSlot()";
            }
        };
    }
}
