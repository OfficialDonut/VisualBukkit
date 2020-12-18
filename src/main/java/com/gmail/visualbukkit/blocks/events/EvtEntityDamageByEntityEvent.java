package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EvtEntityDamageByEntityEvent extends EventBlock {

    public EvtEntityDamageByEntityEvent() {
        super(EntityDamageByEntityEvent.class);
    }
}