package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprBooleanFromString extends Expression {

    public ExprBooleanFromString() {
        super("expr-boolean-from-string", "Boolean From String", "String", "True if the string is \"true\" ignoring case, false otherwise");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("String", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "Boolean.parseBoolean(" + arg(0) + ")";
            }
        };
    }
}
