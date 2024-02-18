package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-negate-boolean", name = "Negate Boolean", description = "Negates a boolean")
public class ExprNegateBoolean extends ExpressionBlock {

    public ExprNegateBoolean() {
        addParameter("Boolean", new ExpressionParameter(ClassInfo.of(boolean.class)));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(boolean.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "(!" + arg(0, buildInfo) + ")";
    }
}
