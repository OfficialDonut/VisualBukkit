package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprCurrentDate extends Expression {

    public ExprCurrentDate() {
        super("expr-current-date", ClassInfo.of("java.time.LocalDateTime"));
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
