package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.VarArgsExpression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprArithmetic extends VarArgsExpression {

    private static final String[] OPERATIONS = {"+", "-", "*", "/", "%"};

    public ExprArithmetic() {
        super("expr-arithmetic", "Arithmetic", "Math", "Arithmetic operations (+, -, *, /, %)");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.DOUBLE;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Number", ClassInfo.DOUBLE), new ChoiceParameter("Operation", OPERATIONS), new ExpressionParameter("Number", ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                String java = null;
                for (int i = 1; i < parameters.length; i += 2) {
                    java = "(" + (java == null ? arg(0) : java) + " " + arg(i) + " " + arg(i + 1) + ")";
                }
                return java;
            }

            @Override
            protected void increaseSize() {
                push(new ChoiceParameter("Operation", OPERATIONS));
                push(new ExpressionParameter("Number", ClassInfo.DOUBLE));
            }

            @Override
            protected void decreaseSize() {
                pop();
                pop();
            }
        };
    }
}
