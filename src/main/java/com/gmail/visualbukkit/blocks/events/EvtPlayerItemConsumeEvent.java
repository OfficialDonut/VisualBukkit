package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class EvtPlayerItemConsumeEvent extends EventBlock {

    public EvtPlayerItemConsumeEvent() {
        super(PlayerItemConsumeEvent.class);
    }
}