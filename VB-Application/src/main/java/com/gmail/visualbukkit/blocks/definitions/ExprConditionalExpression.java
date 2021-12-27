package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprConditionalExpression extends Expression {

    public ExprConditionalExpression() {
        super("expr-conditional-expression", "Conditional Expression", "VB", "Returns one of two objects depending on a condition");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Condition", ClassInfo.BOOLEAN), new ExpressionParameter("If True", ClassInfo.OBJECT), new ExpressionParameter("If False", ClassInfo.OBJECT)) {
            @Override
            public String toJava() {
                return "(" + arg(0) + " ? " + arg(1) + " : " + arg(2) + ")";
            }
        };
    }
}
