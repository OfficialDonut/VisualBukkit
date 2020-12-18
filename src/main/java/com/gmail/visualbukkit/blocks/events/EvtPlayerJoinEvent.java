package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerJoinEvent;

public class EvtPlayerJoinEvent extends EventBlock {

    public EvtPlayerJoinEvent() {
        super(PlayerJoinEvent.class);
    }
}