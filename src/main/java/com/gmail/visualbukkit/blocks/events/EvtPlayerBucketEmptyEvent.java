package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class EvtPlayerBucketEmptyEvent extends EventBlock {

    public EvtPlayerBucketEmptyEvent() {
        super(PlayerBucketEmptyEvent.class);
    }
}