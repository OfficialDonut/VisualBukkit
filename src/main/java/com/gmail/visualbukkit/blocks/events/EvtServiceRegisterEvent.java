package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.server.ServiceRegisterEvent;

public class EvtServiceRegisterEvent extends EventBlock {

    public EvtServiceRegisterEvent() {
        super(ServiceRegisterEvent.class);
    }
}