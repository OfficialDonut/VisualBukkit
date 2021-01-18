package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Category(Category.IO)
@Description("Broadcasts a message to the server")
public class StatBroadcastMessage extends StatementBlock {

    public StatBroadcastMessage() {
        init("broadcast ", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.broadcastMessage(PluginMain.color(" + arg(0) + "));";
    }
}
