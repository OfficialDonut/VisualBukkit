package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Rounds a number to the nearest integer")
public class ExprRoundNumber extends ExpressionBlock<Long> {

    public ExprRoundNumber() {
        init(double.class, " rounded");
    }

    @Override
    public String toJava() {
        return "Math.round(" + arg(0) + ")";
    }
}