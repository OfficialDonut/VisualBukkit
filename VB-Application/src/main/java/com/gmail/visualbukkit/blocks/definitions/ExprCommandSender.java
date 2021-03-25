package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import org.bukkit.command.CommandSender;

public class ExprCommandSender extends Expression {

    public ExprCommandSender() {
        super("expr-command-sender", CommandSender.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-command");
            }

            @Override
            public String toJava() {
                return "commandSender";
            }
        };
    }
}
