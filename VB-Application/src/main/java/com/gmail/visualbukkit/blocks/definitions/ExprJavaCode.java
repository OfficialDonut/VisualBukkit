package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;

public class ExprJavaCode extends Expression {

    public ExprJavaCode() {
        super("expr-java-code");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new InputParameter()) {
            @Override
            public String toJava() {
                return arg(0);
            }
        };
    }
}
