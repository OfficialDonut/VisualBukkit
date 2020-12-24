package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("An empty value")
public class ExprNull extends ExpressionBlock<Object> {

    public ExprNull() {
        init("null");
    }

    @Override
    public String toJava() {
        return "((Object) null)";
    }
}