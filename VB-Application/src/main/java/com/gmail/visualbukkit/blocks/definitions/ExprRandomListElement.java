package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprRandomListElement extends Expression {

    public ExprRandomListElement() {
        super("expr-random-list-element", "Random Element", "List", "A random element of a list");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("List", ClassInfo.LIST)) {
            @Override
            public String toJava() {
                return arg(0) + ".get(java.util.concurrent.ThreadLocalRandom.current().nextInt(" + arg(0) + ".size()))";
            }
        };
    }
}
