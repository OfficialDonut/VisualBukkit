package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprFunctionArguments extends Expression {

    public ExprFunctionArguments() {
        super("expr-function-arguments", "Function Arguments", "VB", "The arguments of a function");
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
                checkForPluginComponent(CompFunction.class);
            }

            @Override
            public String toJava() {
                return "functionArgs";
            }
        };
    }
}
