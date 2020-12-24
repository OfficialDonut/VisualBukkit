package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.util.Vector;

@Description("Constructs a vector from a location")
public class ExprLocationToVector extends ExpressionBlock<Vector> {

    public ExprLocationToVector() {
        init("vector from ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".toVector()";
    }
}