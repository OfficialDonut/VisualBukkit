package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprNumberFromString extends Expression {

    public ExprNumberFromString() {
        super("expr-number-from-string", double.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(String.class)) {
            @Override
            public String toJava() {
                return "Double.parseDouble(" + arg(0) + ")";
            }
        };
    }
}
