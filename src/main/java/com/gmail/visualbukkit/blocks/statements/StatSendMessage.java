package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.command.CommandSender;

@Category(Category.IO)
@Description("Sends a message to a player or console")
public class StatSendMessage extends StatementBlock {

    public StatSendMessage() {
        init("send ", String.class, " to ", CommandSender.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".sendMessage(PluginMain.color(" + arg(0) + "));";
    }
}
