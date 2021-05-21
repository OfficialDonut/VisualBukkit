package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprObjectToString extends Expression {

    public ExprObjectToString() {
        super("expr-object-to-string");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.OBJECT)) {
            @Override
            public String toJava() {
                return "String.valueOf(" + arg(0) + ")";
            }
        };
    }
}
