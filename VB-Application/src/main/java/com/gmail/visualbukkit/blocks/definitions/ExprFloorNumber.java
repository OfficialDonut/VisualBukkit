package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprFloorNumber extends Expression {

    public ExprFloorNumber() {
        super("expr-floor-number", ClassInfo.LONG);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                return "((long) Math.floor(" + arg(0) + "))";
            }
        };
    }
}
