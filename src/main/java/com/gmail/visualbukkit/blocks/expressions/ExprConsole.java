package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.command.ConsoleCommandSender;

@Description("The server console")
public class ExprConsole extends ExpressionBlock<ConsoleCommandSender> {

    public ExprConsole() {
        init("console");
    }

    @Override
    public String toJava() {
        return "Bukkit.getConsoleSender()";
    }
}