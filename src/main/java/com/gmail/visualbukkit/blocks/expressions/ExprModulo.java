package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The remainder after dividing a number by another")
public class ExprModulo extends ExpressionBlock<Double> {

    public ExprModulo() {
        init(double.class, " % ", double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "%" + arg(1) + ")";
    }
}