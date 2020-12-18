package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EvtEntitySpawnEvent extends EventBlock {

    public EvtEntitySpawnEvent() {
        super(EntitySpawnEvent.class);
    }
}