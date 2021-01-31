package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

@Description("Sets the location the player moved to in a PlayerMoveEvent")
public class StatSetMoveToLocation extends StatementBlock {

    public StatSetMoveToLocation() {
        init("set move to location to ", Location.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set move to location must be used in a PlayerMoveEvent", PlayerMoveEvent.class);
    }


    @Override
    public String toJava() {
        return "event.setTo(" + arg(0) + ");";
    }
}
