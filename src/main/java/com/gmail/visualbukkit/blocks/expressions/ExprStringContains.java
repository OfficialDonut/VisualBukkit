package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Checks if a string contains a substring")
public class ExprStringContains extends ExpressionBlock<Boolean> {

    public ExprStringContains() {
        init(String.class, " contains ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".contains(" + arg(2) + ")";
    }
}