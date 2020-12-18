package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.server.ServerLoadEvent;

public class EvtServerLoadEvent extends EventBlock {

    public EvtServerLoadEvent() {
        super(ServerLoadEvent.class);
    }
}