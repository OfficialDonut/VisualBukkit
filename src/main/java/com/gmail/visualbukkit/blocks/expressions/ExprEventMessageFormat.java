package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Description("The message format in an AsyncPlayerChatEvent")
public class ExprEventMessageFormat extends ExpressionBlock<String> {

    public ExprEventMessageFormat() {
        init("message format");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Message format must be used in an AsyncPlayerChatEvent", AsyncPlayerChatEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getFormat()";
    }
}