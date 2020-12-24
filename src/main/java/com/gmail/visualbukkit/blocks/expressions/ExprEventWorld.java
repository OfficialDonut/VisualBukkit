package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;
import org.bukkit.event.world.WorldEvent;

@Description("The world in a WorldEvent")
public class ExprEventWorld extends ExpressionBlock<World> {

    public ExprEventWorld() {
        init("event world");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Event world must be used in a WorldEvent", WorldEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getWorld()";
    }
}