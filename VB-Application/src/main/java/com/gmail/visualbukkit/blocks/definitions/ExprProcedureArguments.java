package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprProcedureArguments extends Expression {

    public ExprProcedureArguments() {
        super("expr-procedure-arguments");
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
                checkForPluginComponent("comp-procedure");
            }

            @Override
            public String toJava() {
                return "procedureArgs";
            }
        };
    }
}
