package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.block.FluidLevelChangeEvent;

public class EvtFluidLevelChangeEvent extends EventBlock {

    public EvtFluidLevelChangeEvent() {
        super(FluidLevelChangeEvent.class);
    }
}