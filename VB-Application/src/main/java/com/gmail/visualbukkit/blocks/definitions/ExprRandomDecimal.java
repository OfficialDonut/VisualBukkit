package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprRandomDecimal extends Expression {

    public ExprRandomDecimal() {
        super("expr-random-decimal", ClassInfo.DOUBLE);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.DOUBLE), new ExpressionParameter(ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                return "java.util.concurrent.ThreadLocalRandom.current().nextDouble(" + arg(0) + "," + arg(1) + "+1)";
            }
        };
    }
}
