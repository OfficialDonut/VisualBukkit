package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;

public class ExprNull extends Expression {

    public ExprNull() {
        super("expr-null", void.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "((Object) null)";
            }
        };
    }
}
