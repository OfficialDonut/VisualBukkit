package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The length of a string")
public class ExprStringLength extends ExpressionBlock<Integer> {

    public ExprStringLength() {
        init("length of ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".length()";
    }
}