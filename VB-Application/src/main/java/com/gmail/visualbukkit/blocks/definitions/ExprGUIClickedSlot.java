package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprGUIClickedSlot extends Expression {

    public ExprGUIClickedSlot() {
        super("expr-gui-clicked-slot", "Clicked Slot", "GUI", "The clicked slot");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.INT;
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
                return "event.getSlot()";
            }
        };
    }
}
