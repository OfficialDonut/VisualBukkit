package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The negation (opposite) of a boolean")
public class ExprNegateBoolean extends ExpressionBlock<Boolean> {

    public ExprNegateBoolean() {
        init("!", boolean.class);
    }

    @Override
    public String toJava() {
        return "!" + arg(0);
    }
}
