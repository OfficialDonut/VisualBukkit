package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.SizedExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;
import java.util.List;
import java.util.StringJoiner;

@BlockDefinition(id = "expr-immutable-list", name = "Immutable List", description = "A new immutable list")
public class ExprImmutableList extends SizedExpressionBlock {

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(List.class);
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
            return "Collections.emptyList()";
        }
        StringJoiner joiner = new StringJoiner(",");
        for (BlockParameter parameter : parameters) {
            joiner.add(parameter.generateJava(buildInfo));
        }
        return "List.of(" + joiner + ")";
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/List.html"));
    }
}
