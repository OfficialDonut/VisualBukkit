package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.SizedExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;
import java.util.StringJoiner;

@BlockDefinition(id = "expr-math", name = "Math", description = "Math operations (+, -, *, /, %)")
public class ExprMath extends SizedExpressionBlock {

    private static final String[] operations = {"+", "-", "*", "/", "%"};

    public ExprMath() {
        addParameter("Number", new ExpressionParameter(ClassInfo.of(double.class)));
        addParameter("Operation", new ChoiceParameter(operations));
        addParameter("Number", new ExpressionParameter(ClassInfo.of(double.class)));
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op1.html"));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(double.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        StringJoiner joiner = new StringJoiner(" ");
        for (BlockParameter parameter : parameters) {
            joiner.add(parameter.generateJava(buildInfo));
        }
        return "(" + joiner + ")";
    }

    @Override
    protected void incrementSize() {
        addParameter("Operation", new ChoiceParameter(operations));
        addParameter("Number", new ExpressionParameter(ClassInfo.of(double.class)));
    }

    @Override
    protected void decrementSize() {
        removeParameters(parameters.size() - 2);
    }
}
