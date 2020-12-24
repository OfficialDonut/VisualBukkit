package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import org.bukkit.entity.Player;

@Category(Category.IO)
@Name("Send JSON Message")
@Description("Sends a JSON message to a player")
public class StatSendJSONMessage extends StatementBlock {

    public StatSendJSONMessage() {
        init("send JSON ", String.class, " to ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".spigot().sendMessage(net.md_5.bungee.chat.ComponentSerializer.parse(" + arg(0) + "));";
    }
}
