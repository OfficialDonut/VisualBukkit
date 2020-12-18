package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerLocaleChangeEvent;

public class EvtPlayerLocaleChangeEvent extends EventBlock {

    public EvtPlayerLocaleChangeEvent() {
        super(PlayerLocaleChangeEvent.class);
    }
}