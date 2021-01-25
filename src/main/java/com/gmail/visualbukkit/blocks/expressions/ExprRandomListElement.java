package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

import java.util.List;

@Description("A random element of a list")
public class ExprRandomListElement extends ExpressionBlock<Object> {

    public ExprRandomListElement() {
        init("random element of ", List.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".get(java.util.concurrent.ThreadLocalRandom.current().nextInt(" + arg(0) + ".size()))";
    }
}