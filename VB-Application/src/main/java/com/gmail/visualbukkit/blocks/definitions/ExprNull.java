package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprNull extends Expression {

    public ExprNull() {
        super("expr-null", "Null", "VB", "The null literal");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.VOID;
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "null";
            }
        };
    }
}
