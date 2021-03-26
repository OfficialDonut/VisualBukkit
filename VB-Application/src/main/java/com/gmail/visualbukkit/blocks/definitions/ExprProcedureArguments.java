package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;

import java.util.List;

public class ExprProcedureArguments extends Expression {

    public ExprProcedureArguments() {
        super("expr-procedure-arguments", List.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-procedure");
            }

            @Override
            public String toJava() {
                return "procedureArgs";
            }
        };
    }
}
