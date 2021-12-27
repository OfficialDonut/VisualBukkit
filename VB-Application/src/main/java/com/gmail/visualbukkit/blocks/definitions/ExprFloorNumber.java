package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprFloorNumber extends Expression {

    public ExprFloorNumber() {
        super("expr-floor-number", "Floor Number", "Math", "Rounds a number down");
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
                return "((long) Math.floor(" + arg(0) + "))";
            }
        };
    }
}
