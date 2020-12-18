package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityPotionEffectEvent;

public class EvtEntityPotionEffectEvent extends EventBlock {

    public EvtEntityPotionEffectEvent() {
        super(EntityPotionEffectEvent.class);
    }
}