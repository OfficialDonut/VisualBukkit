package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class EvtPlayerShearEntityEvent extends EventBlock {

    public EvtPlayerShearEntityEvent() {
        super(PlayerShearEntityEvent.class);
    }
}