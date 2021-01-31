package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

@Description("The location the player moved from in a PlayerMoveEvent")
public class ExprEventMoveFromLocation extends ExpressionBlock<Location> {

    public ExprEventMoveFromLocation() {
        init("move from location");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Move from location must be used in a PlayerMoveEvent", PlayerMoveEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getFrom()";
    }
}
