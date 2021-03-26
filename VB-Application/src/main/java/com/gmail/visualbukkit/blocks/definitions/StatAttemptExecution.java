package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Container;
public class StatAttemptExecution extends Container {

    public StatAttemptExecution() {
        super("stat-attempt-execution");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                if (getNext().hasConnection() && getNext().getConnected().getDefinition().getID().equals("stat-handle-execution-error")) {
                    return "try {" + getChildJava() + "}";
                } else {
                    return "try {" + getChildJava() + "} catch (Exception " + ExprSimpleLocalVariable.getRandomVariable() + ") {}";
                }
            }
        };
    }
}
