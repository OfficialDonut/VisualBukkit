package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The quotient of two numbers")
public class ExprDivision extends ExpressionBlock<Double> {

    public ExprDivision() {
        init(double.class, " / ", double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "/" + arg(1) + ")";
    }
}