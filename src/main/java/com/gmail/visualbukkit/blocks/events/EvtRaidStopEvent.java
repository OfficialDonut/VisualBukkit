package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.raid.RaidStopEvent;

public class EvtRaidStopEvent extends EventBlock {

    public EvtRaidStopEvent() {
        super(RaidStopEvent.class);
    }
}