package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Shuts down the server")
public class StatShutdownServer extends StatementBlock {

    public StatShutdownServer() {
        init("shutdown server");
    }

    @Override
    public String toJava() {
        return "Bukkit.shutdown();";
    }
}
