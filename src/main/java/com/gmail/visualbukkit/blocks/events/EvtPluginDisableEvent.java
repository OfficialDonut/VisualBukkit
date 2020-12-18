package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.server.PluginDisableEvent;

public class EvtPluginDisableEvent extends EventBlock {

    public EvtPluginDisableEvent() {
        super(PluginDisableEvent.class);
    }
}