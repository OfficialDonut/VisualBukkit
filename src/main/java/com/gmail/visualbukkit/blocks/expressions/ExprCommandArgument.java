package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.structures.StructCommand;

@Description("An argument of a command (argument numbers start at 0)")
public class ExprCommandArgument extends ExpressionBlock<String> {

    public ExprCommandArgument() {
        init("argument ", int.class);
    }

    @Override
    public void update() {
        super.update();
        validateStructure("Command argument must be used in a command", StructCommand.class);
    }

    @Override
    public String toJava() {
        return "commandArgs[" + arg(0) + "]";
    }
}