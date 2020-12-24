package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.World;

@Description("A location in a world")
public class ExprLocation extends ExpressionBlock<Location> {

    public ExprLocation() {
        init("location at ", double.class, ", ", double.class, ", ", double.class, " in ", World.class);
    }

    @Override
    public String toJava() {
        return "new Location(" + arg(3) + "," + arg(0) + "," + arg(1) + "," + arg(2) + ")";
    }
}