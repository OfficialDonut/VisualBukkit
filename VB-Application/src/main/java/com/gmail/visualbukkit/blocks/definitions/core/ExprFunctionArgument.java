package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "expr-function-argument", name = "Function Argument", description = "An argument passed to a function (must be used in a 'Function' plugin component)")
public class ExprFunctionArgument extends ExpressionBlock {

    public ExprFunctionArgument() {
        addParameter("Index", new ExpressionParameter(ClassInfo.of(int.class)));
    }

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompFunction.class);
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Function.html"));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "args.get(" + arg(0, buildInfo) + ")";
    }
}
