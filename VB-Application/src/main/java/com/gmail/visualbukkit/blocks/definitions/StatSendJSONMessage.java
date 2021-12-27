package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatSendJSONMessage extends Statement {

    public StatSendJSONMessage() {
        super("stat-send-json-message", "Send JSON Message", "Player", "Sends a JSON message to a player");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Player", ClassInfo.of("org.bukkit.entity.Player")), new ExpressionParameter("Message", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return arg(0) + ".spigot().sendMessage(net.md_5.bungee.chat.ComponentSerializer.parse(" + arg(1) + "));";
            }
        };
    }
}
