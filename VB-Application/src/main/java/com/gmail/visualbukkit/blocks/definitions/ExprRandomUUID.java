package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

import java.util.UUID;

public class ExprRandomUUID extends Expression {

    public ExprRandomUUID() {
        super("expr-random-uuid");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(UUID.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "UUID.randomUUID()";
            }
        };
    }
}
