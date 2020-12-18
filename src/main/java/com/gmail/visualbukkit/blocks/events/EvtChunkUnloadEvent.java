package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.world.ChunkUnloadEvent;

public class EvtChunkUnloadEvent extends EventBlock {

    public EvtChunkUnloadEvent() {
        super(ChunkUnloadEvent.class);
    }
}