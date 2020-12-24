package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The square root of a number")
public class ExprSquareRoot extends ExpressionBlock<Double> {

    public ExprSquareRoot() {
        init("square root of ", double.class);
    }

    @Override
    public String toJava() {
        return "Math.sqrt(" + arg(0) + ")";
    }
}