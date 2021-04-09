package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprSquareRoot extends Expression {

    public ExprSquareRoot() {
        super("expr-square-root", ClassInfo.DOUBLE);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                return "Math.sqrt(" + arg(0) + ")";
            }
        };
    }
}
