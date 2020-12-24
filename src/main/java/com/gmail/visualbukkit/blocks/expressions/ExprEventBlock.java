package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockEvent;

@Description("The block involved in a BlockEvent")
public class ExprEventBlock extends ExpressionBlock<Block> {

    public ExprEventBlock() {
        init("event block");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Event block must be used in a BlockEvent", BlockEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getBlock()";
    }
}