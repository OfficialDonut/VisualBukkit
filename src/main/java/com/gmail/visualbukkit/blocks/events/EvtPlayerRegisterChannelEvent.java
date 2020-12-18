package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

public class EvtPlayerRegisterChannelEvent extends EventBlock {

    public EvtPlayerRegisterChannelEvent() {
        super(PlayerRegisterChannelEvent.class);
    }
}