package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprProcedureArgument extends Expression {

    public ExprProcedureArgument() {
        super("expr-procedure-argument", Object.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(int.class)) {
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
