package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.world.WorldSaveEvent;

public class EvtWorldSaveEvent extends EventBlock {

    public EvtWorldSaveEvent() {
        super(WorldSaveEvent.class);
    }
}