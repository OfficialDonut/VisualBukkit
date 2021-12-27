package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprCommandSender extends Expression {

    public ExprCommandSender() {
        super("expr-command-sender", "Command Sender", "Command", "The player/console who executed a command");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.command.CommandSender");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent(CompCommand.class);
            }

            @Override
            public String toJava() {
                return "commandSender";
            }
        };
    }
}
