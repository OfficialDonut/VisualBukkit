package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Container;
import org.apache.commons.lang3.RandomStringUtils;

public class StatHandleExecutionError extends Container {

    public StatHandleExecutionError() {
        super("stat-handle-execution-error", "Handle Execution Error", "VB", "Runs code if an error occurred in the previous attempt execution block");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPrevious(StatAttemptExecution.class);
            }

            @Override
            public String toJava() {
                return "catch (Exception " + RandomStringUtils.randomAlphabetic(16) + ") {" + getChildJava() + "}";
            }
        };
    }
}
