package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityTargetEvent;

public class EvtEntityTargetEvent extends EventBlock {

    public EvtEntityTargetEvent() {
        super(EntityTargetEvent.class);
    }
}