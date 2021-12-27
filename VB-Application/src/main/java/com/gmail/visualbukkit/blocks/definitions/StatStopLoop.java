package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;

public class StatStopLoop extends Statement {

    public StatStopLoop() {
        super("stat-stop-loop", "Stop Loop", "VB", "Stops a loop");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                setValid(checkForContainer(StatListLoop.class) || checkForContainer(StatNumberLoop.class) || checkForContainer(StatWhileLoop.class));
            }

            @Override
            public String toJava() {
                return "if (true) break;";
            }
        };
    }
}
