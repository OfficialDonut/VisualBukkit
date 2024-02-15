package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.MultilineInputParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-java-code", name = "Java Code", description = "Arbitrary Java code")
public class ExprJavaCode extends ExpressionBlock {

    public ExprJavaCode() {
        addParameter("Java", new MultilineInputParameter());
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT_OR_PRIMITIVE;
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return arg(0, buildInfo);
    }
}
