package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class EvtPrepareItemEnchantEvent extends EventBlock {

    public EvtPrepareItemEnchantEvent() {
        super(PrepareItemEnchantEvent.class);
    }
}