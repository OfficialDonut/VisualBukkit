package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.block.BlockDamageEvent;

public class EvtBlockDamageEvent extends EventBlock {

    public EvtBlockDamageEvent() {
        super(BlockDamageEvent.class);
    }
}