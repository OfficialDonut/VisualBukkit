package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ClassParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "expr-is-class", name = "Is Class", description = "Checks if an object is an instance of a class")
public class ExprIsClass extends ExpressionBlock {

    private final ClassParameter classParameter = new ClassParameter();

    public ExprIsClass() {
        addParameter("Class", classParameter);
        addParameter("Object", new ExpressionParameter(ClassInfo.of(Object.class)));
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Class.html"));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(boolean.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return classParameter.getValue() != null ? arg(0, buildInfo) + ".class.isAssignableFrom(" + arg(1, buildInfo) + ".getClass())" : "false";
    }
}
