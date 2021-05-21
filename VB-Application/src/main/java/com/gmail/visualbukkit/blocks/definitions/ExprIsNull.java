package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprIsNull extends Expression {

    public ExprIsNull() {
        super("expr-is-null");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.OBJECT)) {
            @Override
            public String toJava() {
                return "(" + arg(0) + "== null)";
            }
        };
    }
}
