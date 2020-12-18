package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class EvtPrepareItemCraftEvent extends EventBlock {

    public EvtPrepareItemCraftEvent() {
        super(PrepareItemCraftEvent.class);
    }
}