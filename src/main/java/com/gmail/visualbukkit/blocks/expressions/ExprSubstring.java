package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("A substring of a string")
public class ExprSubstring extends ExpressionBlock<String> {

    public ExprSubstring() {
        init("substring of ", String.class, " from ", int.class, " to ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".substring(" + arg(1) + "," + arg(2) + ")";
    }
}