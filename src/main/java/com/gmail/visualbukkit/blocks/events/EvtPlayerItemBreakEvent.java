package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerItemBreakEvent;

public class EvtPlayerItemBreakEvent extends EventBlock {

    public EvtPlayerItemBreakEvent() {
        super(PlayerItemBreakEvent.class);
    }
}