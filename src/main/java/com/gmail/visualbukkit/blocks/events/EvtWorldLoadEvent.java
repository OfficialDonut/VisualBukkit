package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.world.WorldLoadEvent;

public class EvtWorldLoadEvent extends EventBlock {

    public EvtWorldLoadEvent() {
        super(WorldLoadEvent.class);
    }
}