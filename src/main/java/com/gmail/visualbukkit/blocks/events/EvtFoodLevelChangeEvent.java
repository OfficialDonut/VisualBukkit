package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class EvtFoodLevelChangeEvent extends EventBlock {

    public EvtFoodLevelChangeEvent() {
        super(FoodLevelChangeEvent.class);
    }
}