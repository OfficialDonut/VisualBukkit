package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprMergeStringList extends Expression {

    public ExprMergeStringList() {
        super("expr-merge-string-list");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.STRING), new ExpressionParameter(ClassInfo.LIST)) {
            @Override
            public String toJava() {
                return "String.join(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }
}
