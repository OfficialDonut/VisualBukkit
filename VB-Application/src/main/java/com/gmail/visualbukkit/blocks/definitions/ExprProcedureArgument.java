package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprProcedureArgument extends Expression {

    public ExprProcedureArgument() {
        super("expr-procedure-argument", "Procedure Argument", "VB", "An argument of a procedure");
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
                checkForPluginComponent(CompProcedure.class);
            }

            @Override
            public String toJava() {
                return "procedureArgs.get(" + arg(0) + ")";
            }
        };
    }
}
