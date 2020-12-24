package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Generates a random decimal between two numbers (inclusive)")
public class ExprRandomDecimal extends ExpressionBlock<Double> {

    public ExprRandomDecimal() {
        init("random decimal between ", double.class, " and ", double.class);
    }

    @Override
    public String toJava() {
        return "java.util.concurrent.ThreadLocalRandom.current().nextDouble(" + arg(0) + "," + arg(1) + "+1)";
    }
}