package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprSquareRoot extends Expression {

    public ExprSquareRoot() {
        super("expr-square-root", "Square Root", "Math", "The square root of a number");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.DOUBLE;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Number", ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                return "Math.sqrt(" + arg(0) + ")";
            }
        };
    }
}
