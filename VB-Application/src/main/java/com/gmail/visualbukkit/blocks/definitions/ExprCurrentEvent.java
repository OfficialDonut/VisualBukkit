package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import org.bukkit.event.Event;

public class ExprCurrentEvent extends Expression {

    public ExprCurrentEvent() {
        super("expr-current-event", Event.class);
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
