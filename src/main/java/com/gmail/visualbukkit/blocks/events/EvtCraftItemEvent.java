package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.inventory.CraftItemEvent;

public class EvtCraftItemEvent extends EventBlock {

    public EvtCraftItemEvent() {
        super(CraftItemEvent.class);
    }
}