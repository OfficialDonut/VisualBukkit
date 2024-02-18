package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-loop-value", name = "Loop Value", description = "The current loop value")
public class ExprLoopValue extends ExpressionBlock {

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Object.class);
    }

    @Override
    public void updateState() {
        super.updateState();
        checkForContainer(StatListLoop.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "$FINAL_loopValue" + getNestedContainers(StatListLoop.class);
    }
}
