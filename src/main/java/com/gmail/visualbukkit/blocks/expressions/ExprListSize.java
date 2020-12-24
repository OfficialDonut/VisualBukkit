package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("The size of a list")
public class ExprListSize extends ExpressionBlock<Integer> {

    public ExprListSize() {
        init("size of ", List.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".size()";
    }
}