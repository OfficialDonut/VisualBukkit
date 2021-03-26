package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;

import java.util.List;

public class ExprCommandArguments extends Expression {

    public ExprCommandArguments() {
        super("expr-command-arguments", List.class);
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
                return "PluginMain.createList(commandArgs)";
            }
        };
    }
}
