package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerTeleportEvent;

@Description("The teleport cause in a PlayerTeleportEvent")
public class ExprEventTeleportCause extends ExpressionBlock<PlayerTeleportEvent.TeleportCause> {

    public ExprEventTeleportCause() {
        init("teleport cause");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Teleport cause must be used in a PlayerTeleportEvent", PlayerTeleportEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getCause()";
    }
}