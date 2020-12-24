package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The product of two numbers")
public class ExprMultiplication extends ExpressionBlock<Double> {

    public ExprMultiplication() {
        init(double.class, " * ", double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "*" + arg(1) + ")";
    }
}