package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Description("The message in an AsyncPlayerChatEvent")
public class ExprEventMessage extends ExpressionBlock<String> {

    public ExprEventMessage() {
        init("message");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Message must be used in an AsyncPlayerChatEvent", AsyncPlayerChatEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getMessage()";
    }
}