package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.inventory.InventoryEvent;

public class EvtInventoryEvent extends EventBlock {

    public EvtInventoryEvent() {
        super(InventoryEvent.class);
    }
}