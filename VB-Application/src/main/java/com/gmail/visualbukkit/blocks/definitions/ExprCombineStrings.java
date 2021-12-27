package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.VarArgsExpression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprCombineStrings extends VarArgsExpression {

    public ExprCombineStrings() {
        super("expr-combine-strings", "Combine Strings", "String", "Concatenates strings");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("String", ClassInfo.STRING), new ExpressionParameter("String", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                String java = arg(0);
                for (int i = 1; i < parameters.length; i ++) {
                    java = "(" + java + " + " + arg(i) + ")";
                }
                return java;
            }

            @Override
            protected void increaseSize() {
                push(new ExpressionParameter("String", ClassInfo.STRING));
            }

            @Override
            protected void decreaseSize() {
                pop();
            }
        };
    }
}
