package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EvtEntityTargetLivingEntityEvent extends EventBlock {

    public EvtEntityTargetLivingEntityEvent() {
        super(EntityTargetLivingEntityEvent.class);
    }
}