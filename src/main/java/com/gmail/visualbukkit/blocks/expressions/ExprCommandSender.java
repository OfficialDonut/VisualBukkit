package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.structures.StructCommand;
import org.bukkit.command.CommandSender;

@Description("The executor of a command")
public class ExprCommandSender extends ExpressionBlock<CommandSender> {

    public ExprCommandSender() {
        init("command sender");
    }

    @Override
    public void update() {
        super.update();
        validateStructure("Command sender must be used in a command", StructCommand.class);
    }

    @Override
    public String toJava() {
        return "commandSender";
    }
}
