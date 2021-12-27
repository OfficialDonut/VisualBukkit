package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprCommandArguments extends Expression {

    public ExprCommandArguments() {
        super("expr-command-arguments", "Command Arguments", "Bukkit", "The arguments of a command");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.LIST;
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
                return "PluginMain.createList(commandArgs)";
            }
        };
    }
}
