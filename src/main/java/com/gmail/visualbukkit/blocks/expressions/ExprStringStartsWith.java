package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Checks if a string starts with another string")
public class ExprStringStartsWith extends ExpressionBlock<Boolean> {

    public ExprStringStartsWith() {
        init(String.class, " starts with ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".startsWith(" + arg(2) + ")";
    }
}