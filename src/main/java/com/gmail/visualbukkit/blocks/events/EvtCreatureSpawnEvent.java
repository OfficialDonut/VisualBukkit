package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EvtCreatureSpawnEvent extends EventBlock {

    public EvtCreatureSpawnEvent() {
        super(CreatureSpawnEvent.class);
    }
}