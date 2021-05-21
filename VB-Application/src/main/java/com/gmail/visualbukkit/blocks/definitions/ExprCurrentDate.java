package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

import java.time.LocalDateTime;

public class ExprCurrentDate extends Expression {

    public ExprCurrentDate() {
        super("expr-current-date");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(LocalDateTime.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "java.time.LocalDateTime.now()";
            }
        };
    }
}
