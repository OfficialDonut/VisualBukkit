package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerJoinEvent;

@Description("The join message in a PlayerJoinEvent")
public class ExprEventJoinMessage extends ExpressionBlock<String> {

    public ExprEventJoinMessage() {
        init("join message");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Join message must be used in a PlayerJoinEvent", PlayerJoinEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getJoinMessage()";
    }
}