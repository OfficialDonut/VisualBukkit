package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprAbsoluteValue extends Expression {

    public ExprAbsoluteValue() {
        super("expr-absolute-value", "Absolute Value", "Math", "The absolute value of a number");
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
                return "Math.abs(" + arg(0) + ")";
            }
        };
    }
}
