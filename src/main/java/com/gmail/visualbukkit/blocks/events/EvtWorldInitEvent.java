package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.world.WorldInitEvent;

public class EvtWorldInitEvent extends EventBlock {

    public EvtWorldInitEvent() {
        super(WorldInitEvent.class);
    }
}