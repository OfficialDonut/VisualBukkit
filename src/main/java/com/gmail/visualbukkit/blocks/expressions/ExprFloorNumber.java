package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Rounds a number down")
public class ExprFloorNumber extends ExpressionBlock<Integer> {

    public ExprFloorNumber() {
        init("floor ", double.class);
    }

    @Override
    public String toJava() {
        return "((int) Math.floor(" + arg(0) + "))";
    }
}