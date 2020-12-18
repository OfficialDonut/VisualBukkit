package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class EvtPlayerLevelChangeEvent extends EventBlock {

    public EvtPlayerLevelChangeEvent() {
        super(PlayerLevelChangeEvent.class);
    }
}