package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.world.ChunkPopulateEvent;

public class EvtChunkPopulateEvent extends EventBlock {

    public EvtChunkPopulateEvent() {
        super(ChunkPopulateEvent.class);
    }
}