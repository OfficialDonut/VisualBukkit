package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprNumberFromString extends Expression {

    public ExprNumberFromString() {
        super("expr-number-from-string", "Number From String", "String", "Converts a string to a number");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.DOUBLE;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("String", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "Double.parseDouble(" + arg(0) + ")";
            }
        };
    }
}
