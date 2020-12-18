package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

public class EvtPlayerUnleashEntityEvent extends EventBlock {

    public EvtPlayerUnleashEntityEvent() {
        super(PlayerUnleashEntityEvent.class);
    }
}