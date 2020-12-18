package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityTransformEvent;

public class EvtEntityTransformEvent extends EventBlock {

    public EvtEntityTransformEvent() {
        super(EntityTransformEvent.class);
    }
}