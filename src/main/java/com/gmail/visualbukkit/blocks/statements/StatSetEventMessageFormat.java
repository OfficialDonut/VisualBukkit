package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Description("Sets the message format in an AsyncPlayerChatEvent")
public class StatSetEventMessageFormat extends StatementBlock {

    public StatSetEventMessageFormat() {
        init("set message format to ", String.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set message format must be used in an AsyncPlayerChatEvent", AsyncPlayerChatEvent.class);
    }

    @Override
    public String toJava() {
        return "event.setFormat(" + arg(0) + ");";
    }
}
