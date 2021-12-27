package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Container;

public class StatElseStatement extends Container {

    public StatElseStatement() {
        super("stat-else-statement", "Else Statement", "VB", "Runs code if the condition of the previous if statement is false");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                setValid(checkForPrevious(StatIfStatement.class) || checkForPrevious(StatElseIfStatement.class));
            }

            @Override
            public String toJava() {
                return "else {" + getChildJava() + "}";
            }
        };
    }
}
