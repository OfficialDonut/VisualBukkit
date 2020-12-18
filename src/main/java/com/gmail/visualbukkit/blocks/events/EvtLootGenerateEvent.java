package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.world.LootGenerateEvent;

public class EvtLootGenerateEvent extends EventBlock {

    public EvtLootGenerateEvent() {
        super(LootGenerateEvent.class);
    }
}