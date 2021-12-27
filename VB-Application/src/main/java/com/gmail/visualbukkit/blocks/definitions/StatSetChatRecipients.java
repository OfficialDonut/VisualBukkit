package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatSetChatRecipients extends Statement {

    public StatSetChatRecipients() {
        super("stat-set-chat-recipients", "Set Chat Recipients", "AsyncPlayerChatEvent", "Sets the message recipients");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Recipients", ClassInfo.LIST)) {
            @Override
            public void update() {
                super.update();
                checkForEvent(ClassInfo.of("org.bukkit.event.player.AsyncPlayerChatEvent"));
            }

            @Override
            public String toJava() {
                return "event.getRecipients().clear(); event.getRecipients().addAll(" + arg(0) + ");";
            }
        };
    }
}
