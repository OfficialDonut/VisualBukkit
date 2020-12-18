package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.hanging.HangingBreakEvent;

public class EvtHangingBreakEvent extends EventBlock {

    public EvtHangingBreakEvent() {
        super(HangingBreakEvent.class);
    }
}