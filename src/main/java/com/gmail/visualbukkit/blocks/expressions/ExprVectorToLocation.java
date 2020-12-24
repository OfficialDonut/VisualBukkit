package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

@Description("Gets a location from a vector")
public class ExprVectorToLocation extends ExpressionBlock<Location> {

    public ExprVectorToLocation() {
        init("location from ", Vector.class, " in ", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".toLocation(" + arg(1) + ")";
    }
}