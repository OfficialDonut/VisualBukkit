package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerLoginEvent;

public class EvtPlayerLoginEvent extends EventBlock {

    public EvtPlayerLoginEvent() {
        super(PlayerLoginEvent.class);
    }
}