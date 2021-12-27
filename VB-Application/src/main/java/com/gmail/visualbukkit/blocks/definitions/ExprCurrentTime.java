package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprCurrentTime extends Expression {

    public ExprCurrentTime() {
        super("expr-current-time", "Current Time", "System", "The current time in milliseconds");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.LONG;
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "System.currentTimeMillis()";
            }
        };
    }
}
