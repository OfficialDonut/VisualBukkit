package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.Container;

public class StatHandleExecutionError extends Container {

    public StatHandleExecutionError() {
        super("stat-handle-execution-error");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                if (getPrevious() instanceof ChildConnector || !(getPrevious().getOwner().getDefinition() instanceof StatAttemptExecution)) {
                    setInvalid(VisualBukkitApp.getString("error.invalid_handle_error"));
                }
            }

            @Override
            public String toJava() {
                return "catch (Exception " + ExprSimpleLocalVariable.getRandomVariable() + ") {" + getChildJava() + "}";
            }
        };
    }
}
