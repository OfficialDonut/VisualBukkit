package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatSendJSONMessage extends Statement {

    public StatSendJSONMessage() {
        super("stat-send-json-message");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.entity.Player")), new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return arg(0) + ".spigot().sendMessage(net.md_5.bungee.chat.ComponentSerializer.parse(" + arg(1) + "));";
            }
        };
    }
}
