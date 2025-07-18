package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "expr-number-comparison", name = "Number Comparison", description = "Compares two numbers")
public class ExprNumberComparison extends ExpressionBlock {

    public ExprNumberComparison() {
        addParameter("Number", new ExpressionParameter(ClassInfo.of(double.class)));
        addParameter("Operator", new ChoiceParameter("==", "!=", "<", "<=", ">", ">="));
        addParameter("Number", new ExpressionParameter(ClassInfo.of(double.class)));
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html"));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(boolean.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "(" + arg(0, buildInfo) + " " + arg(1, buildInfo) + " " + arg(2, buildInfo) + ")";
    }
}
