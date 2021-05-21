package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.Statement;
import org.apache.commons.lang3.RandomStringUtils;

public class StatAttemptExecution extends Container {

    public StatAttemptExecution() {
        super("stat-attempt-execution");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                Statement.Block next = getStatementHolder().getNext(this);
                if (next != null && next.getDefinition().getID().equals("stat-handle-execution-error")) {
                    return "try {" + getChildJava() + "}";
                } else {
                    return "try {" + getChildJava() + "} catch (Exception " + RandomStringUtils.randomAlphabetic(16) + ") {}";
                }
            }
        };
    }
}
