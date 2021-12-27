package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprLoopValue extends Expression {

    public ExprLoopValue() {
        super("expr-loop-value", "Loop Value", "VB", "The current loop value");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForContainer(StatListLoop.class);
            }

            @Override
            public String toJava() {
                return "FINAL_loopValue" + StatListLoop.getNestedLoops(this);
            }
        };
    }
}
