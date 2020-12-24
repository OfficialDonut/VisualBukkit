package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Returns a value based on a condition")
public class ExprConditionalExpression extends ExpressionBlock<Object> {

    public ExprConditionalExpression() {
        init(Object.class, " if ", boolean.class, " else ", Object.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(1) + "?" + arg(0) + ":" + arg(2) + ")";
    }
}