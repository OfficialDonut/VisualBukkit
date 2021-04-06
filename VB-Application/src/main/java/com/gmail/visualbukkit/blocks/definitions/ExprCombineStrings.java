package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprCombineStrings extends Expression {

    public ExprCombineStrings() {
        super("expr-combine-strings", ClassInfo.STRING);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.STRING), new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "(" + arg(0) + "+" + arg(1) + ")";
            }
        };
    }
}
