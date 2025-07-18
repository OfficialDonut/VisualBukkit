package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.SizedExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;
import java.util.StringJoiner;

@BlockDefinition(id = "expr-string-concatenation", name = "String Concatenation", description = "Concatenates two or more strings")
public class ExprStringConcatenation extends SizedExpressionBlock {

    public ExprStringConcatenation() {
        addParameter("String", new ExpressionParameter(ClassInfo.of(String.class)));
        addParameter("String", new ExpressionParameter(ClassInfo.of(String.class)));
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#concat(java.lang.String)"));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(String.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        StringJoiner joiner = new StringJoiner(" + ");
        for (BlockParameter parameter : parameters) {
            joiner.add(parameter.generateJava(buildInfo));
        }
        return "(" + joiner + ")";
    }

    @Override
    protected void incrementSize() {
        addParameter("String", new ExpressionParameter(ClassInfo.of(String.class)));
    }

    @Override
    protected void decrementSize() {
        removeParameters(parameters.size() - 1);
    }
}
