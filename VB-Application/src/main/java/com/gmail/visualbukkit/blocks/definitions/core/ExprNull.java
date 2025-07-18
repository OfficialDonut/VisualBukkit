package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "expr-null", name = "Null", description = "A null reference")
public class ExprNull extends ExpressionBlock {

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(Object.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "((Object) null)";
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://www.upwork.com/resources/what-is-null-in-java"));
    }
}
