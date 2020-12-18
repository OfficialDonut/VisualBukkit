package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityDamageEvent;

public class EvtEntityDamageEvent extends EventBlock {

    public EvtEntityDamageEvent() {
        super(EntityDamageEvent.class);
    }
}