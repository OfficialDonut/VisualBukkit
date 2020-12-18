package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.weather.LightningStrikeEvent;

public class EvtLightningStrikeEvent extends EventBlock {

    public EvtLightningStrikeEvent() {
        super(LightningStrikeEvent.class);
    }
}