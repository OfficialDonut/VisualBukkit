package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class EvtAsyncPlayerPreLoginEvent extends EventBlock {

    public EvtAsyncPlayerPreLoginEvent() {
        super(AsyncPlayerPreLoginEvent.class);
    }
}