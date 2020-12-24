package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;

@Description("The distance between two locations")
public class ExprLocationDistance extends ExpressionBlock<Double> {

    public ExprLocationDistance() {
        init("distance between ", Location.class, " and ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".distance(" + arg(1) + ")";
    }
}