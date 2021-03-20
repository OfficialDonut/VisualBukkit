package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;

public class ExprLoopNumber extends Expression {

    public ExprLoopNumber() {
        super("expr-loop-number", int.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForContainer("stat-number-loop");
            }

            @Override
            public String toJava() {
                return "FINAL_loopNumber" + StatNumberLoop.getNestedLoops(this);
            }
        };
    }
}
