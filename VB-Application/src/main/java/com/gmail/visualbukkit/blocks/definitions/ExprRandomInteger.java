package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprRandomInteger extends Expression {

    public ExprRandomInteger() {
        super("expr-random-integer", "Random Integer", "Math", "A random integer between a min value (inclusive) and max value (exclusive)");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.INT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Min", ClassInfo.INT), new ExpressionParameter("Max", ClassInfo.INT)) {
            @Override
            public String toJava() {
                return "java.util.concurrent.ThreadLocalRandom.current().nextInt(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }
}
