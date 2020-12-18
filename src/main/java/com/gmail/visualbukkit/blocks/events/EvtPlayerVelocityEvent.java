package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerVelocityEvent;

public class EvtPlayerVelocityEvent extends EventBlock {

    public EvtPlayerVelocityEvent() {
        super(PlayerVelocityEvent.class);
    }
}