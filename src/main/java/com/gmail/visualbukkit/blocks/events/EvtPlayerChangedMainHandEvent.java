package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerChangedMainHandEvent;

public class EvtPlayerChangedMainHandEvent extends EventBlock {

    public EvtPlayerChangedMainHandEvent() {
        super(PlayerChangedMainHandEvent.class);
    }
}