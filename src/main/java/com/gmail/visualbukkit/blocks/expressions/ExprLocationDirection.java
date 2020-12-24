package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.util.Vector;

@Description("A unit-vector in the direction of a location's yaw/pitch")
public class ExprLocationDirection extends ExpressionBlock<Vector> {

    public ExprLocationDirection() {
        init("direction of ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getDirection()";
    }
}