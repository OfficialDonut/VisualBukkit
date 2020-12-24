package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

@Description("The percentage of blocks to drop in a BlockExplodeEvent or EntityExplodeEvent")
public class ExprEventExplosionYield extends ExpressionBlock<Float> {

    public ExprEventExplosionYield() {
        init("explosion yield");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Explosion yield must be used in a BlockExplodeEvent or EntityExplodeEvent", BlockExplodeEvent.class, EntityExplodeEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getYield()";
    }
}