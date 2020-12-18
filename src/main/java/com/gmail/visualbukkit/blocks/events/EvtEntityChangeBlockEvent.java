package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EvtEntityChangeBlockEvent extends EventBlock {

    public EvtEntityChangeBlockEvent() {
        super(EntityChangeBlockEvent.class);
    }
}