package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.SizedExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.ArrayList;
import java.util.StringJoiner;

@BlockDefinition(id = "expr-list", name = "List", description = "A new list")
public class ExprList extends SizedExpressionBlock {

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(ArrayList.class);
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
            return "new ArrayList()";
        }
        StringJoiner joiner = new StringJoiner(",");
        for (BlockParameter parameter : parameters) {
            joiner.add(parameter.generateJava(buildInfo));
        }
        return "new ArrayList(Arrays.asList(" + joiner + "))";
    }
}
