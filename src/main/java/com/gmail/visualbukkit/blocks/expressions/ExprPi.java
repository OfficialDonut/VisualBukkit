package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The value of pi")
public class ExprPi extends ExpressionBlock<Double> {

    public ExprPi() {
        init("pi");
    }

    @Override
    public String toJava() {
        return "Math.PI";
    }
}