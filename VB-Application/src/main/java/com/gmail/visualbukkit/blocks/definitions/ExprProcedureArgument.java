package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprProcedureArgument extends Expression {

    public ExprProcedureArgument() {
        super("expr-procedure-argument", ClassInfo.OBJECT);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.INT)) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-procedure");
            }

            @Override
            public String toJava() {
                return "procedureArgs.get(" + arg(0) + ")";
            }
        };
    }
}
