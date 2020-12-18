package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityCombustEvent;

public class EvtEntityCombustEvent extends EventBlock {

    public EvtEntityCombustEvent() {
        super(EntityCombustEvent.class);
    }
}