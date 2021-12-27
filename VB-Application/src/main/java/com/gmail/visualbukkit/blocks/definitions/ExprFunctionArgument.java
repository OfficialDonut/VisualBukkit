package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprFunctionArgument extends Expression {

    public ExprFunctionArgument() {
        super("expr-function-argument", "Function Argument", "VB", "An argument of a function");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Index", ClassInfo.INT)) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent(CompFunction.class);
            }

            @Override
            public String toJava() {
                return "functionArgs.get(" + arg(0) + ")";
            }
        };
    }
}
