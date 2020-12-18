package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityBreakDoorEvent;

public class EvtEntityBreakDoorEvent extends EventBlock {

    public EvtEntityBreakDoorEvent() {
        super(EntityBreakDoorEvent.class);
    }
}