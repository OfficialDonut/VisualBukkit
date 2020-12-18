package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntitySpellCastEvent;

public class EvtEntitySpellCastEvent extends EventBlock {

    public EvtEntitySpellCastEvent() {
        super(EntitySpellCastEvent.class);
    }
}