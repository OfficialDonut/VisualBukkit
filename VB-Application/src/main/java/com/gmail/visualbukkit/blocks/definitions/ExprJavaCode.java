package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;

public class ExprJavaCode extends Expression {

    public ExprJavaCode() {
        super("expr-java-code", "Java Code", "VB", "Raw Java code");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new InputParameter("Java")) {
            @Override
            public String toJava() {
                return arg(0);
            }
        };
    }
}
