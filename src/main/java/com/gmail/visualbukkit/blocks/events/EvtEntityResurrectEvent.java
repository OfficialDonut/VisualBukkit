package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityResurrectEvent;

public class EvtEntityResurrectEvent extends EventBlock {

    public EvtEntityResurrectEvent() {
        super(EntityResurrectEvent.class);
    }
}