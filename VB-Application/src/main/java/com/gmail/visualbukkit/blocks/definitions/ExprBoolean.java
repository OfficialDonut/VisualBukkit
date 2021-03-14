package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.BinaryChoiceParameter;

public class ExprBoolean extends Expression {

    public ExprBoolean() {
        super("expr-boolean", boolean.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new BinaryChoiceParameter("true", "false")) {
            @Override
            public String toJava() {
                return arg(0);
            }
        };
    }
}
