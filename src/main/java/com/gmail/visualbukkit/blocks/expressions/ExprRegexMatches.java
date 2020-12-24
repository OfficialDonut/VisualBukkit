package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Checks if a string matches a regex")
public class ExprRegexMatches extends ExpressionBlock<Boolean> {

    public ExprRegexMatches() {
        init(String.class, " matches regex ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".matches(" + arg(1) + ")";
    }
}