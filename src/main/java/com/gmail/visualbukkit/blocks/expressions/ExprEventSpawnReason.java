package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.CreatureSpawnEvent;

@Description("The spawn reason in a CreatureSpawnEvent")
public class ExprEventSpawnReason extends ExpressionBlock<CreatureSpawnEvent.SpawnReason> {

    public ExprEventSpawnReason() {
        init("spawn reason");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Spawn reason must be used in a CreatureSpawnEvent", CreatureSpawnEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getSpawnReason()";
    }
}