package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerInteractEvent;

public class EvtPlayerInteractEvent extends EventBlock {

    public EvtPlayerInteractEvent() {
        super(PlayerInteractEvent.class);
    }
}