package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatFunctionReturn extends Statement {

    public StatFunctionReturn() {
        super("stat-function-return");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(Object.class)) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-function");
            }

            @Override
            public String toJava() {
                return "if (true) return " + arg(0) + ";";
            }
        };
    }
}
