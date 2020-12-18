package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EvtPlayerTeleportEvent extends EventBlock {

    public EvtPlayerTeleportEvent() {
        super(PlayerTeleportEvent.class);
    }
}