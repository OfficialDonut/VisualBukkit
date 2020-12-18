package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class EvtPlayerCommandPreprocessEvent extends EventBlock {

    public EvtPlayerCommandPreprocessEvent() {
        super(PlayerCommandPreprocessEvent.class);
    }
}