package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;

@Description("The yaw of a location")
public class ExprLocationYaw extends ExpressionBlock<Float> {

    public ExprLocationYaw() {
        init("yaw of ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getYaw()";
    }
}