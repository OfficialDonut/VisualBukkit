package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.block.Block;

@Description("The block at a location")
public class ExprBlockAtLocation extends ExpressionBlock<Block> {

    public ExprBlockAtLocation() {
        init("block at ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getBlock()";
    }
}