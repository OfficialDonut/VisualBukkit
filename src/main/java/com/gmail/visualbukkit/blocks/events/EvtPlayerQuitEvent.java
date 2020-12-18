package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerQuitEvent;

public class EvtPlayerQuitEvent extends EventBlock {

    public EvtPlayerQuitEvent() {
        super(PlayerQuitEvent.class);
    }
}