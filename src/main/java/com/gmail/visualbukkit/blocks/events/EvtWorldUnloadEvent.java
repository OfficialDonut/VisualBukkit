package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.world.WorldUnloadEvent;

public class EvtWorldUnloadEvent extends EventBlock {

    public EvtWorldUnloadEvent() {
        super(WorldUnloadEvent.class);
    }
}