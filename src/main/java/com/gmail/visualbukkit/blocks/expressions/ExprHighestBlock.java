package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.block.Block;

@Description("The highest block at a location")
public class ExprHighestBlock extends ExpressionBlock<Block> {

    public ExprHighestBlock() {
        init("highest block at ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWorld().getHighestBlockAt(" + arg(0) + ")";
    }
}