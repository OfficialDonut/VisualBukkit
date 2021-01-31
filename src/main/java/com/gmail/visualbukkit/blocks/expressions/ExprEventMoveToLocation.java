package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

@Description("The location the player moved to in a PlayerMoveEvent")
public class ExprEventMoveToLocation extends ExpressionBlock<Location> {

    public ExprEventMoveToLocation() {
        init("move to location");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Move to location must be used in a PlayerMoveEvent", PlayerMoveEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getTo()";
    }
}
