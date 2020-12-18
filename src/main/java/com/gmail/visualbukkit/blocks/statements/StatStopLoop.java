package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Stops a loop")
public class StatStopLoop extends StatementBlock {

    public StatStopLoop() {
        init("stop loop");
    }

    @Override
    public void update() {
        super.update();
        validateParent("Stop loop must be used in a loop", StatNumberLoop.class, StatListLoop.class, StatWhileLoop.class);
    }

    @Override
    public String toJava() {
        return "break;";
    }
}
