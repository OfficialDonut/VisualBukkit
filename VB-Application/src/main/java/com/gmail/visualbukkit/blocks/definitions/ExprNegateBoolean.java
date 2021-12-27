package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprNegateBoolean extends Expression {

    public ExprNegateBoolean() {
        super("expr-negate-boolean", "Negate Boolean", "Math", "Negates a boolean");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Boolean", ClassInfo.BOOLEAN)) {
            @Override
            public String toJava() {
                return "(!" + arg(0) + ")";
            }
        };
    }
}
