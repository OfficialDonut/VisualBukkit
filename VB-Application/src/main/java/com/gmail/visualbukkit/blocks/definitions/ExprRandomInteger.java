package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprRandomInteger extends Expression {

    public ExprRandomInteger() {
        super("expr-random-integer", ClassInfo.INT);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.INT), new ExpressionParameter(ClassInfo.INT)) {
            @Override
            public String toJava() {
                return "java.util.concurrent.ThreadLocalRandom.current().nextInt(" + arg(0) + "," + arg(1) + "+1)";
            }
        };
    }
}
