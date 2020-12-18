package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityCombustByBlockEvent;

public class EvtEntityCombustByBlockEvent extends EventBlock {

    public EvtEntityCombustByBlockEvent() {
        super(EntityCombustByBlockEvent.class);
    }
}