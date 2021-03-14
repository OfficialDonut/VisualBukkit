package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatExecuteExpression extends Statement {

    public StatExecuteExpression() {
        super("stat-execute-expression");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(Object.class)) {
            @Override
            public String toJava() {
                return arg(0) + ";";
            }
        };
    }
}
