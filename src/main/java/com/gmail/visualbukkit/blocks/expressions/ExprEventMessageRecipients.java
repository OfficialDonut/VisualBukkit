package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

@Description("The message recipients in an AsyncPlayerChatEvent")
@SuppressWarnings("rawtypes")
public class ExprEventMessageRecipients extends ExpressionBlock<List> {

    public ExprEventMessageRecipients() {
        init("message recipients");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Message recipients must be used in an AsyncPlayerChatEvent", AsyncPlayerChatEvent.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(event.getRecipients())";
    }
}