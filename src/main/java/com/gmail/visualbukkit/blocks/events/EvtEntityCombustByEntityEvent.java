package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityCombustByEntityEvent;

public class EvtEntityCombustByEntityEvent extends EventBlock {

    public EvtEntityCombustByEntityEvent() {
        super(EntityCombustByEntityEvent.class);
    }
}