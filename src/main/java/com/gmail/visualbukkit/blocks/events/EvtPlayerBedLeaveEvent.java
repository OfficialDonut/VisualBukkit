package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class EvtPlayerBedLeaveEvent extends EventBlock {

    public EvtPlayerBedLeaveEvent() {
        super(PlayerBedLeaveEvent.class);
    }
}