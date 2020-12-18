package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityEnterBlockEvent;

public class EvtEntityEnterBlockEvent extends EventBlock {

    public EvtEntityEnterBlockEvent() {
        super(EntityEnterBlockEvent.class);
    }
}