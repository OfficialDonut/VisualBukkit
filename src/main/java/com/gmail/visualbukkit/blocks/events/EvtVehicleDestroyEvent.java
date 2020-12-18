package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class EvtVehicleDestroyEvent extends EventBlock {

    public EvtVehicleDestroyEvent() {
        super(VehicleDestroyEvent.class);
    }
}