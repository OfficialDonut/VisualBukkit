package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Makes a string all lowercase")
public class ExprLowercaseString extends ExpressionBlock<String> {

    public ExprLowercaseString() {
        init(String.class, " in lowercase");
    }

    @Override
    public String toJava() {
        return arg(0) + ".toLowerCase()";
    }
}