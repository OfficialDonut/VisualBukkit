package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class EvtPlayerGameModeChangeEvent extends EventBlock {

    public EvtPlayerGameModeChangeEvent() {
        super(PlayerGameModeChangeEvent.class);
    }
}