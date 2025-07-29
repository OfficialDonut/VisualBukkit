package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.PluginComponentParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;
import java.util.List;

@BlockDefinition(id = "expr-function-value", name = "Function Value", description = "Evaluates the given function")
public class ExprFunctionValue extends ExpressionBlock {

    public ExprFunctionValue() {
        addParameter("Function", new PluginComponentParameter(CompFunction.class));
        addParameter("Arguments", new ExpressionParameter(ClassInfo.of(List.class)));
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
        return "PluginMain.function(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + ")";
    }
}
