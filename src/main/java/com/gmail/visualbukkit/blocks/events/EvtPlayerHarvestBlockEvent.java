package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

public class EvtPlayerHarvestBlockEvent extends EventBlock {

    public EvtPlayerHarvestBlockEvent() {
        super(PlayerHarvestBlockEvent.class);
    }
}