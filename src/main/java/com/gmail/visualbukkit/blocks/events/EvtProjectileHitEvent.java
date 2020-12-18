package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.ProjectileHitEvent;

public class EvtProjectileHitEvent extends EventBlock {

    public EvtProjectileHitEvent() {
        super(ProjectileHitEvent.class);
    }
}