package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

public class EvtEntityDamageByBlockEvent extends EventBlock {

    public EvtEntityDamageByBlockEvent() {
        super(EntityDamageByBlockEvent.class);
    }
}