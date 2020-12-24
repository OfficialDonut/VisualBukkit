package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The difference of two numbers")
public class ExprSubtraction extends ExpressionBlock<Double> {

    public ExprSubtraction() {
        init(double.class, " - ", double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "-" + arg(1) + ")";
    }
}