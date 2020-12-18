package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EvtAsyncPlayerChatEvent extends EventBlock {

    public EvtAsyncPlayerChatEvent() {
        super(AsyncPlayerChatEvent.class);
    }
}