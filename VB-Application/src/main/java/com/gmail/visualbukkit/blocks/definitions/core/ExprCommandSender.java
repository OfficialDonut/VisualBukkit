package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-command-sender", name = "Command Sender", description = "The player/console that executed the command (must be used in a 'Command' plugin component)")
public class ExprCommandSender extends ExpressionBlock {

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.command.CommandSender");
    }

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompCommand.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "commandSender";
    }
}
