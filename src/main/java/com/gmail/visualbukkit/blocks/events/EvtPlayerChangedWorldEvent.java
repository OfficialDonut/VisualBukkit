package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class EvtPlayerChangedWorldEvent extends EventBlock {

    public EvtPlayerChangedWorldEvent() {
        super(PlayerChangedWorldEvent.class);
    }
}