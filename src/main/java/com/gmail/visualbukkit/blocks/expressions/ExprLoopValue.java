package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.statements.StatListLoop;

@Description("The current element in a list loop")
public class ExprLoopValue extends ExpressionBlock<Object> {

    public ExprLoopValue() {
        init("loop value");
    }

    @Override
    public void update() {
        super.update();
        validateParent("Loop value must be used in a list loop", StatListLoop.class);
    }

    @Override
    public String toJava() {
        return "loopValue" + (StatListLoop.getNestedLoops(getStatement()) - 1);
    }
}
