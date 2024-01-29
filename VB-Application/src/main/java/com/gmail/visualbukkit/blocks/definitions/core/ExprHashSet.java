package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.SizedExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.HashSet;
import java.util.StringJoiner;

@BlockDefinition(id = "expr-hashset", name = "HashSet", description = "A new HashSet")
public class ExprHashSet extends SizedExpressionBlock {

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(HashSet.class);
    }

    @Override
    protected void incrementSize() {
        addParameter("Object", new ExpressionParameter(ClassInfo.of(Object.class)));
    }

    @Override
    protected void decrementSize() {
        removeParameters(parameters.size() - 1);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        if (parameters == null) {
            return "new HashSet()";
        }
        StringJoiner joiner = new StringJoiner(",");
        for (BlockParameter parameter : parameters) {
            joiner.add(parameter.generateJava(buildInfo));
        }
        return "new HashSet(Arrays.asList(" + joiner + "))";
    }
}
