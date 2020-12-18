package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class EvtPlayerSwapHandItemsEvent extends EventBlock {

    public EvtPlayerSwapHandItemsEvent() {
        super(PlayerSwapHandItemsEvent.class);
    }
}