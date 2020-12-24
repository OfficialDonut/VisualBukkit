package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The sum of two numbers")
public class ExprAddition extends ExpressionBlock<Double> {

    public ExprAddition() {
        init(double.class, " + ", double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "+" + arg(1) + ")";
    }
}