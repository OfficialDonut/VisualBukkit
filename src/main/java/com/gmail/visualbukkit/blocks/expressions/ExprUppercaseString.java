package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Makes a string all uppercase")
public class ExprUppercaseString extends ExpressionBlock<String> {

    public ExprUppercaseString() {
        init(String.class, " in uppercase");
    }

    @Override
    public String toJava() {
        return arg(0) + ".toUpperCase()";
    }
}