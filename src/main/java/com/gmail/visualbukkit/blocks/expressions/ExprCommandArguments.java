package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.structures.StructCommand;

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