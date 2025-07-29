package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "expr-procedure-argument", name = "Procedure Argument", description = "An argument passed to a procedure (must be used in a 'Procedure' plugin component)")
public class ExprProcedureArgument extends ExpressionBlock {

    public ExprProcedureArgument() {
        addParameter("Index", new ExpressionParameter(ClassInfo.of(int.class)));
    }

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompProcedure.class);
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html"));
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
