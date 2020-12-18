package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.server.ServiceUnregisterEvent;

public class EvtServiceUnregisterEvent extends EventBlock {

    public EvtServiceUnregisterEvent() {
        super(ServiceUnregisterEvent.class);
    }
}