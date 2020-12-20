package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Runs a console command")
public class StatRunCommand extends StatementBlock {

    public StatRunCommand() {
        init("run console command ", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.dispatchCommand(Bukkit.getConsoleSender()," + arg(0) + ");";
    }
}
