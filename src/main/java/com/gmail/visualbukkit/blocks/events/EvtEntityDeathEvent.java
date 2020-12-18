package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityDeathEvent;

public class EvtEntityDeathEvent extends EventBlock {

    public EvtEntityDeathEvent() {
        super(EntityDeathEvent.class);
    }
}