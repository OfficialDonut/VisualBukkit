package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

public class EvtHangingBreakByEntityEvent extends EventBlock {

    public EvtHangingBreakByEntityEvent() {
        super(HangingBreakByEntityEvent.class);
    }
}