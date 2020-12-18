package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class EvtPlayerItemHeldEvent extends EventBlock {

    public EvtPlayerItemHeldEvent() {
        super(PlayerItemHeldEvent.class);
    }
}