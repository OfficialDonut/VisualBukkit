package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.block.Block;

@Description("The location of a block")
public class ExprBlockLocation extends ExpressionBlock<Location> {

    public ExprBlockLocation() {
        init("location of ", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getLocation()";
    }
}