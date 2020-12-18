package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EvtPlayerDeathEvent extends EventBlock {

    public EvtPlayerDeathEvent() {
        super(PlayerDeathEvent.class);
    }
}