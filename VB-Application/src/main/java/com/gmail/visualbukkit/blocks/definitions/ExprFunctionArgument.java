package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprFunctionArgument extends Expression {

    public ExprFunctionArgument() {
        super("expr-function-argument");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.INT)) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-function");
            }

            @Override
            public String toJava() {
                return "functionArgs.get(" + arg(0) + ")";
            }
        };
    }
}
