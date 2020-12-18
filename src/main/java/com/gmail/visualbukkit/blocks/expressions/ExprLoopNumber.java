package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.statements.StatNumberLoop;

@Description("The current number in a number loop")
public class ExprLoopNumber extends ExpressionBlock<Integer> {

    public ExprLoopNumber() {
        init("loop number");
    }

    @Override
    public void update() {
        super.update();
        validateParent("Loop number must be used in a number loop", StatNumberLoop.class);
    }

    @Override
    public String toJava() {
        return "loopNumber" + (StatNumberLoop.getNestedLoops(getStatement()) - 1);
    }
}
