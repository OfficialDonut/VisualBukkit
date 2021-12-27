package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprExponentiation extends Expression {

    public ExprExponentiation() {
        super("expr-exponentiation", "Exponentiation", "Math", "Raises a number to a power");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.DOUBLE;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Base", ClassInfo.DOUBLE), new ExpressionParameter("Exponent", ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                return "Math.pow(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }
}
