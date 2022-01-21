package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;

public class StatContinueLoop extends Statement {

    public StatContinueLoop() {
        super("stat-continue-loop", "Continue Loop", "VB", "Advances a loop to the next iteration");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                setValid(checkForContainer(StatListLoop.class) || checkForContainer(StatNumberLoop.class) || checkForContainer(StatAdvancedNumberLoop.class));
            }

            @Override
            public String toJava() {
                return "if (true) continue;";
            }
        };
    }
}
