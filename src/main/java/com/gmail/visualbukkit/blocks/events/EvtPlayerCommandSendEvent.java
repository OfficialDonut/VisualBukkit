package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class EvtPlayerCommandSendEvent extends EventBlock {

    public EvtPlayerCommandSendEvent() {
        super(PlayerCommandSendEvent.class);
    }
}