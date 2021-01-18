package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.structures.StructCommand;

import java.util.List;

@Description("The arguments of a command")
@SuppressWarnings("rawtypes")
public class ExprCommandArguments extends ExpressionBlock<List> {

    public ExprCommandArguments() {
        init("arguments");
    }

    @Override
    public void update() {
        super.update();
        validateStructure("Command arguments must be used in a command", StructCommand.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(commandArgs)";
    }
}