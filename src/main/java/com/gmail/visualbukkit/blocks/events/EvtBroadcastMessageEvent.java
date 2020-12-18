package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.server.BroadcastMessageEvent;

public class EvtBroadcastMessageEvent extends EventBlock {

    public EvtBroadcastMessageEvent() {
        super(BroadcastMessageEvent.class);
    }
}