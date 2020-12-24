package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Checks if an object is null")
public class ExprIsNull extends ExpressionBlock<Boolean> {

    public ExprIsNull() {
        init(Object.class, " is null");
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "== null)";
    }
}