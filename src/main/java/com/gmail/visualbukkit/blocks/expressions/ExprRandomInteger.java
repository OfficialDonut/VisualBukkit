package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Generates a random integer between two numbers (inclusive)")
public class ExprRandomInteger extends ExpressionBlock<Integer> {

    public ExprRandomInteger() {
        init("random integer between ", int.class, " and ", int.class);
    }

    @Override
    public String toJava() {
        return "java.util.concurrent.ThreadLocalRandom.current().nextInt(" + arg(0) + "," + arg(1) + "+1)";
    }
}