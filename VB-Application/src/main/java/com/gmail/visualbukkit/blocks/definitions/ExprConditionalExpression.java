package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprConditionalExpression extends Expression {

    public ExprConditionalExpression() {
        super("expr-conditional-expression");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.BOOLEAN), new ExpressionParameter(ClassInfo.OBJECT), new ExpressionParameter(ClassInfo.OBJECT)) {
            @Override
            public String toJava() {
                return "(" + arg(0) + " ? " + arg(1) + " : " + arg(2) + ")";
            }
        };
    }
}
