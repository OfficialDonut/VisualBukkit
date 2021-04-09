package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprCeilNumber extends Expression {

    public ExprCeilNumber() {
        super("expr-ceil-number", ClassInfo.LONG);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                return "((long) Math.ceil(" + arg(0) + "))";
            }
        };
    }
}
