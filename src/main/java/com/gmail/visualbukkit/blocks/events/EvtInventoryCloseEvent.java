package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class EvtInventoryCloseEvent extends EventBlock {

    public EvtInventoryCloseEvent() {
        super(InventoryCloseEvent.class);
    }
}