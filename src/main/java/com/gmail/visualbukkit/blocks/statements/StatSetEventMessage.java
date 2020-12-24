package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Description("Sets the message in an AsyncPlayerChatEvent")
public class StatSetEventMessage extends StatementBlock {

    public StatSetEventMessage() {
        init("set message to ", String.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set message must be used in an AsyncPlayerChatEvent", AsyncPlayerChatEvent.class);
    }

    @Override
    public String toJava() {
        return "event.setMessage(" + arg(0) + ");";
    }
}
