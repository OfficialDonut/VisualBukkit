package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The absolute value of a number")
public class ExprAbsoluteValue extends ExpressionBlock<Double> {

    public ExprAbsoluteValue() {
        init("absolute value of ", double.class);
    }

    @Override
    public String toJava() {
        return "Math.abs(" + arg(0) + ")";
    }
}