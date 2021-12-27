package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprRandomBoolean extends Expression {

    public ExprRandomBoolean() {
        super("expr-random-boolean", "Random Boolean", "Math", "A random boolean");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "java.util.concurrent.ThreadLocalRandom.current().nextBoolean()";
            }
        };
    }
}
