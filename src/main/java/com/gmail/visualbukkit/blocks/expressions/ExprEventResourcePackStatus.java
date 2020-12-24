package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

@Description("The resource pack status in a PlayerResourcePackStatusEvent")
public class ExprEventResourcePackStatus extends ExpressionBlock<PlayerResourcePackStatusEvent.Status> {

    public ExprEventResourcePackStatus() {
        init("resource pack status");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Resource pack status must be used in a PlayerResourcePackStatusEvent", PlayerResourcePackStatusEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getStatus()";
    }
}