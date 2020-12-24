package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Raises a number to a power")
public class ExprExponentiation extends ExpressionBlock<Double> {

    public ExprExponentiation() {
        init(double.class, " ^ ", double.class);
    }

    @Override
    public String toJava() {
        return "Math.pow(" + arg(0) + "," + arg(1) + ")";
    }
}