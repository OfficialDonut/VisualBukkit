package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The index of a substring in a string")
public class ExprSubstringIndex extends ExpressionBlock<Integer> {

    public ExprSubstringIndex() {
        init("index of ", String.class, " in ", String.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".indexOf(" + arg(0) + ")";
    }
}