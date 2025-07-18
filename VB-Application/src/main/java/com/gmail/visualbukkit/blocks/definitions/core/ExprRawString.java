package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "expr-raw-string", name = "Raw String", description = "A string in which escape sequences can be used")
public class ExprRawString extends ExpressionBlock {

    public ExprRawString() {
        addParameter("String", new InputParameter());
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/data/strings/index.html"));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(String.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "\"" + arg(0, buildInfo) + "\"";
    }
}
