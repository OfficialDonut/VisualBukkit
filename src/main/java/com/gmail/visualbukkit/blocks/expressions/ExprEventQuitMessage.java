package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerQuitEvent;

@Description("The quit message in a PlayerQuitEvent")
public class ExprEventQuitMessage extends ExpressionBlock<String> {

    public ExprEventQuitMessage() {
        init("quit message");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Quit message must be used in a PlayerQuitEvent", PlayerQuitEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getQuitMessage()";
    }
}