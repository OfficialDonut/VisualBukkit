package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("Forces a player to send a message")
public class StatForceChat extends StatementBlock {

    public StatForceChat() {
        init("force ", Player.class, " to chat ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".chat(" + arg(1) + ");";
    }
}
