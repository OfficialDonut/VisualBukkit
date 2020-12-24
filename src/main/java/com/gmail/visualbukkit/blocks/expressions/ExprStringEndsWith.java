package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Checks if a string ends with another string")
public class ExprStringEndsWith extends ExpressionBlock<Boolean> {

    public ExprStringEndsWith() {
        init(String.class, " ends with ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".endsWith(" + arg(2) + ")";
    }
}