package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatCommandReturn extends Statement {

    public StatCommandReturn() {
        super("stat-command-return", "Command Return", "Bukkit", "Terminates a command and indicates whether it was successful.\nIf false is returned, then the command usage will be sent.");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Success", ClassInfo.BOOLEAN)) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent(CompCommand.class);
            }

            @Override
            public String toJava() {
                return "if (true) return " + arg(0) + ";";
            }
        };
    }
}
