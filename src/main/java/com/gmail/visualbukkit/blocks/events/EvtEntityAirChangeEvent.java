package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityAirChangeEvent;

public class EvtEntityAirChangeEvent extends EventBlock {

    public EvtEntityAirChangeEvent() {
        super(EntityAirChangeEvent.class);
    }
}