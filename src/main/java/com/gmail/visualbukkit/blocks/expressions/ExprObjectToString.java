package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The string representation of an object")
public class ExprObjectToString extends ExpressionBlock<String> {

    public ExprObjectToString() {
        init(Object.class, " to string");
    }

    @Override
    public String toJava() {
        return "String.valueOf(" + arg(0) + ")";
    }
}