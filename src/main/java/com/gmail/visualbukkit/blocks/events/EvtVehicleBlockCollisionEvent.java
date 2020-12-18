package com.gmail.visualbukkit.blocks.events;

import com.gmail.visualbukkit.blocks.EventBlock;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;

public class EvtVehicleBlockCollisionEvent extends EventBlock {

    public EvtVehicleBlockCollisionEvent() {
        super(VehicleBlockCollisionEvent.class);
    }
}