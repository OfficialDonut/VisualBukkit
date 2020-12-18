package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;

public class EvtPlayerUnregisterChannelEvent extends EventBlock {

    public EvtPlayerUnregisterChannelEvent() {
        super(PlayerUnregisterChannelEvent.class);
    }
}