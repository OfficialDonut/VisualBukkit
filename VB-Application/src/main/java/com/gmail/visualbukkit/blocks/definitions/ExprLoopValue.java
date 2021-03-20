package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;

public class ExprLoopValue extends Expression {

    public ExprLoopValue() {
        super("expr-loop-value", int.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForContainer("stat-list-loop");
            }

            @Override
            public String toJava() {
                return "FINAL_loopValue" + StatListLoop.getNestedLoops(this);
            }
        };
    }
}
