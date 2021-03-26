package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;

import java.util.List;

public class ExprFunctionArguments extends Expression {

    public ExprFunctionArguments() {
        super("expr-function-arguments", List.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-function");
            }

            @Override
            public String toJava() {
                return "functionArgs";
            }
        };
    }
}
