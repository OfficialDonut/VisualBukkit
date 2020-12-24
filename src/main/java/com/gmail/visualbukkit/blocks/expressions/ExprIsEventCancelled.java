package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Checks if an event is cancelled")
public class ExprIsEventCancelled extends ExpressionBlock<Boolean> {

    public ExprIsEventCancelled() {
        init("event is cancelled");
    }

    @Override
    public String toJava() {
        return "event.isCancelled()";
    }
}