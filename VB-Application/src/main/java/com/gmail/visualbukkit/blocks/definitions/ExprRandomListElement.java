package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprRandomListElement extends Expression {

    public ExprRandomListElement() {
        super("expr-random-list-element", ClassInfo.OBJECT);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.LIST)) {
            @Override
            public String toJava() {
                return arg(0) + ".get(java.util.concurrent.ThreadLocalRandom.current().nextInt(" + arg(0) + ".size()))";
            }
        };
    }
}
