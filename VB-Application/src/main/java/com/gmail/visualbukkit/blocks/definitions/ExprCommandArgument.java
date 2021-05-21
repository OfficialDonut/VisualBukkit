package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprCommandArgument extends Expression {

    public ExprCommandArgument() {
        super("expr-command-argument");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.INT)) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-command");
            }

            @Override
            public String toJava() {
                return "(commandArgs.length > " + arg(0) + " ? commandArgs[" + arg(0) + "] : null" + ")";
            }
        };
    }
}
