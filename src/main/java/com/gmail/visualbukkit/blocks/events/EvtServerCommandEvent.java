package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.server.ServerCommandEvent;

public class EvtServerCommandEvent extends EventBlock {

    public EvtServerCommandEvent() {
        super(ServerCommandEvent.class);
    }
}