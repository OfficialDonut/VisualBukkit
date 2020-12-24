package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The current time in milliseconds")
public class ExprCurrentTime extends ExpressionBlock<Long> {

    public ExprCurrentTime() {
        init("current time");
    }

    @Override
    public String toJava() {
        return "System.currentTimeMillis()";
    }
}