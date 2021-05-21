package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprLoopNumber extends Expression {

    public ExprLoopNumber() {
        super("expr-loop-number");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.INT;
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
