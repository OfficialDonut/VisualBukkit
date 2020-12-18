package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.block.BlockBurnEvent;

public class EvtBlockBurnEvent extends EventBlock {

    public EvtBlockBurnEvent() {
        super(BlockBurnEvent.class);
    }
}