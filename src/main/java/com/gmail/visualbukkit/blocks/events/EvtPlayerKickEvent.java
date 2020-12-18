package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerKickEvent;

public class EvtPlayerKickEvent extends EventBlock {

    public EvtPlayerKickEvent() {
        super(PlayerKickEvent.class);
    }
}