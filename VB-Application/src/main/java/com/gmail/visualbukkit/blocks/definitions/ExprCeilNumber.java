package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprCeilNumber extends Expression {

    public ExprCeilNumber() {
        super("expr-ceil-number", "Ceil Number", "Math", "Rounds a number up");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.LONG;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Number", ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                return "((long) Math.ceil(" + arg(0) + "))";
            }
        };
    }
}
