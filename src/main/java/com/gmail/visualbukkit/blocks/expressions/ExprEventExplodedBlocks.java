package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

@Description("The blocks that will be removed in a BlockExplodeEvent or EntityExplodeEvent (mutable list)")
@SuppressWarnings("rawtypes")
public class ExprEventExplodedBlocks extends ExpressionBlock<List> {

    public ExprEventExplodedBlocks() {
        init("exploded blocks");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Exploded blocks must be used in a BlockExplodeEvent or EntityExplodeEvent", BlockExplodeEvent.class, EntityExplodeEvent.class);
    }

    @Override
    public String toJava() {
        return "event.blockList()";
    }
}