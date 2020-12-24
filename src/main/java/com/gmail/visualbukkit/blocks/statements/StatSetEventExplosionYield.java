package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

@Description("Sets the percentage of blocks to drop in a BlockExplodeEvent or EntityExplodeEvent")
public class StatSetEventExplosionYield extends StatementBlock {

    public StatSetEventExplosionYield() {
        init("set explosion yield to ", float.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set explosion yield must be used in a BlockExplodeEvent or EntityExplodeEvent", BlockExplodeEvent.class, EntityExplodeEvent.class);
    }

    @Override
    public String toJava() {
        return "event.setYield(" + arg(0) + ");";
    }
}
