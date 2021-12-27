package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprRandomNumber extends Expression {

    public ExprRandomNumber() {
        super("expr-random-number", "Random Number", "Math", "A random number between a min value (inclusive) and max value (exclusive)");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.DOUBLE;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Min", ClassInfo.DOUBLE), new ExpressionParameter("Max", ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                return "java.util.concurrent.ThreadLocalRandom.current().nextDouble(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }
}
