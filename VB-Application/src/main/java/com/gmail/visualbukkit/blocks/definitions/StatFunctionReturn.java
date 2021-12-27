package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatFunctionReturn extends Statement {

    public StatFunctionReturn() {
        super("stat-function-return", "Function Return", "VB", "Returns a value from a function");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Value", ClassInfo.OBJECT)) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent(CompFunction.class);
            }

            @Override
            public String toJava() {
                return "if (true) return " + arg(0) + ";";
            }
        };
    }
}
