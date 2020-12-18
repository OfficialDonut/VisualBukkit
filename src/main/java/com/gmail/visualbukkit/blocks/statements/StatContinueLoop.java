package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Advances a loop to the next iteration")
public class StatContinueLoop extends StatementBlock {

    public StatContinueLoop() {
        init("continue loop");
    }

    @Override
    public void update() {
        super.update();
        validateParent("Continue loop must be used in a loop", StatNumberLoop.class, StatListLoop.class, StatWhileLoop.class);
    }

    @Override
    public String toJava() {
        return "continue;";
    }
}
