package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprCurrentEvent extends Expression {

    public ExprCurrentEvent() {
        super("expr-current-event");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.event.Event");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-event-listener");
            }

            @Override
            public String toJava() {
                return "((Event) event)";
            }
        };
    }
}
