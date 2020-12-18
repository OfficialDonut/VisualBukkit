package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.ItemDespawnEvent;

public class EvtItemDespawnEvent extends EventBlock {

    public EvtItemDespawnEvent() {
        super(ItemDespawnEvent.class);
    }
}