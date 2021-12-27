package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprRoundNumber extends Expression {

    public ExprRoundNumber() {
        super("expr-round-number", "Round Number", "Math", "Rounds a number (half-up");
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
                return "Math.round(" + arg(0) + ")";
            }
        };
    }
}
