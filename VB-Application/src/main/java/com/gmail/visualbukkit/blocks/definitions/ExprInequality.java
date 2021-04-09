package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprInequality extends Expression {

    public ExprInequality() {
        super("expr-inequality", ClassInfo.BOOLEAN);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ChoiceParameter("<", "<=", ">", ">="), new ExpressionParameter(ClassInfo.DOUBLE), new ExpressionParameter(ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                return "(" + arg(1) + arg(0) + arg(2) + ")";
            }
        };
    }
}
