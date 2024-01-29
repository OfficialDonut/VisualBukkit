package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-execution-exception", name = "Execution Exception", description = "The exception that occurred (must be used in 'Handle Exception')")
public class ExprExecutionException extends ExpressionBlock {

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Exception.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "$executionException" + getNestedContainers(StatHandleException.class);
    }
}
