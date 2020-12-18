package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.block.BlockCanBuildEvent;

public class EvtBlockCanBuildEvent extends EventBlock {

    public EvtBlockCanBuildEvent() {
        super(BlockCanBuildEvent.class);
    }
}