package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EvtEntityRegainHealthEvent extends EventBlock {

    public EvtEntityRegainHealthEvent() {
        super(EntityRegainHealthEvent.class);
    }
}