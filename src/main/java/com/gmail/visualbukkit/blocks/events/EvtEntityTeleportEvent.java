package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityTeleportEvent;

public class EvtEntityTeleportEvent extends EventBlock {

    public EvtEntityTeleportEvent() {
        super(EntityTeleportEvent.class);
    }
}