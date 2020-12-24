package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Rounds a number up")
public class ExprCeilNumber extends ExpressionBlock<Long> {

    public ExprCeilNumber() {
        init("ceil ", double.class);
    }

    @Override
    public String toJava() {
        return "((long) Math.ceil(" + arg(0) + "))";
    }
}