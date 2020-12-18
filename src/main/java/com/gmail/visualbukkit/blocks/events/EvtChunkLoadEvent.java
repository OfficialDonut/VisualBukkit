package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.world.ChunkLoadEvent;

public class EvtChunkLoadEvent extends EventBlock {

    public EvtChunkLoadEvent() {
        super(ChunkLoadEvent.class);
    }
}