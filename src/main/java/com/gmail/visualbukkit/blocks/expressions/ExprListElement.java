package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("The element at an index in a list")
public class ExprListElement extends ExpressionBlock<Object> {

    public ExprListElement() {
        init("element ", int.class, " of ", List.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".get(" + arg(0) + ")";
    }
}