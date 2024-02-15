package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-is-null", name = "Is Null", description = "Checks if an object is null")
public class ExprIsNull extends ExpressionBlock {

    public ExprIsNull() {
        addParameter("Object", new ExpressionParameter(ClassInfo.of(Object.class)));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(boolean.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "(" + arg(0, buildInfo) + " == null)";
    }
}
