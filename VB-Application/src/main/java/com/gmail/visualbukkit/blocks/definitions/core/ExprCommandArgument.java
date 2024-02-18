package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-command-argument", name = "Command Argument", description = "An argument passed to a command (must be used in a 'Command' plugin component)")
public class ExprCommandArgument extends ExpressionBlock {

    public ExprCommandArgument() {
        addParameter("Index", new ExpressionParameter(ClassInfo.of(int.class)));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(String.class);
    }

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompCommand.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "(commandArgs.length > " + arg(0, buildInfo) + " ? commandArgs[" + arg(0, buildInfo) + "] : null" + ")";
    }
}
