package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Checks if a list is empty")
public class ExprIsListEmpty extends ExpressionBlock<Boolean> {

    public ExprIsListEmpty() {
        init(List.class, " is empty");
    }

    @Override
    public String toJava() {
        return arg(0) + ".isEmpty()";
    }
}