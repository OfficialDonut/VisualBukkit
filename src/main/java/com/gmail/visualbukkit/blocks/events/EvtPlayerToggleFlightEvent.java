package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class EvtPlayerToggleFlightEvent extends EventBlock {

    public EvtPlayerToggleFlightEvent() {
        super(PlayerToggleFlightEvent.class);
    }
}