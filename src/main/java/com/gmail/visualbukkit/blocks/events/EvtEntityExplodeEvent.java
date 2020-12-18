package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EvtEntityExplodeEvent extends EventBlock {

    public EvtEntityExplodeEvent() {
        super(EntityExplodeEvent.class);
    }
}