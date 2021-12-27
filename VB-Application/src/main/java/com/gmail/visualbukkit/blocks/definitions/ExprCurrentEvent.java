package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.generated.EventComponent;

public class ExprCurrentEvent extends Expression {

    public ExprCurrentEvent() {
        super("expr-current-event", "Current Event", "Bukkit", "The current event");
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
                checkForPluginComponent(EventComponent.class);
            }

            @Override
            public String toJava() {
                return "((Event) event)";
            }
        };
    }
}
