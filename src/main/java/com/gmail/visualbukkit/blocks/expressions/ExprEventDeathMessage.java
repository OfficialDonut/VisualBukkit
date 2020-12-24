package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.PlayerDeathEvent;

@Description("The death message in a PlayerDeathEvent")
public class ExprEventDeathMessage extends ExpressionBlock<String> {

    public ExprEventDeathMessage() {
        init("death message");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Death message must be used in a PlayerDeathEvent", PlayerDeathEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getDeathMessage()";
    }
}