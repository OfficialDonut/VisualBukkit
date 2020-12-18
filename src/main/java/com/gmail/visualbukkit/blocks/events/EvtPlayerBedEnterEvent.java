package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class EvtPlayerBedEnterEvent extends EventBlock {

    public EvtPlayerBedEnterEvent() {
        super(PlayerBedEnterEvent.class);
    }
}