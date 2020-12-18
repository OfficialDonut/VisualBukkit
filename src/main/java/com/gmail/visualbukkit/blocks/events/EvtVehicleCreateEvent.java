package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.vehicle.VehicleCreateEvent;

public class EvtVehicleCreateEvent extends EventBlock {

    public EvtVehicleCreateEvent() {
        super(VehicleCreateEvent.class);
    }
}