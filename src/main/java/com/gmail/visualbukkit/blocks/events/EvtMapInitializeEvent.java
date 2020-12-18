package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.server.MapInitializeEvent;

public class EvtMapInitializeEvent extends EventBlock {

    public EvtMapInitializeEvent() {
        super(MapInitializeEvent.class);
    }
}