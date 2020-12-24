package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("Checks if a list contains an object")
public class ExprListContains extends ExpressionBlock<Boolean> {

    public ExprListContains() {
        init(List.class, " contains ", Object.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".contains(" + arg(1) + ")";
    }
}