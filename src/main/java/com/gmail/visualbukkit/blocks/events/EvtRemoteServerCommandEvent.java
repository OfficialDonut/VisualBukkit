package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.server.RemoteServerCommandEvent;

public class EvtRemoteServerCommandEvent extends EventBlock {

    public EvtRemoteServerCommandEvent() {
        super(RemoteServerCommandEvent.class);
    }
}