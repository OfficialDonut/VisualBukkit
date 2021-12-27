package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprInequality extends Expression {

    public ExprInequality() {
        super("expr-inequality", "Inequality", "Math", "Compare two numbers");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Number", ClassInfo.DOUBLE), new ChoiceParameter("Inequality", "<", "<=", ">", ">="), new ExpressionParameter("Number", ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                return "(" + arg(0) + " " + arg(1) + " " + arg(2) + ")";
            }
        };
    }
}
