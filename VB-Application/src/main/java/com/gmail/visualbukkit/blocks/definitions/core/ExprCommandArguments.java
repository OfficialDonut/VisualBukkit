package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.List;

@BlockDefinition(id = "expr-command-arguments", name = "Command Arguments", description = "The list of arguments passed to a command (must be used in a 'Command' or 'Tab Complete Handler' plugin component)")
public class ExprCommandArguments extends ExpressionBlock {

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(List.class);
    }

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompCommand.class, CompTabCompleteHandler.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "List.of(commandArgs)";
    }
}
