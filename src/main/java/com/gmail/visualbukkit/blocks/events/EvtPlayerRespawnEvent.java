package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerRespawnEvent;

public class EvtPlayerRespawnEvent extends EventBlock {

    public EvtPlayerRespawnEvent() {
        super(PlayerRespawnEvent.class);
    }
}