package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprCurrentTime extends Expression {

    public ExprCurrentTime() {
        super("expr-current-time", ClassInfo.LONG);
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
