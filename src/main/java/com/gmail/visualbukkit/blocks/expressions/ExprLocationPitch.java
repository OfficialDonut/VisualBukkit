package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;

@Description("The pitch of a location")
public class ExprLocationPitch extends ExpressionBlock<Float> {

    public ExprLocationPitch() {
        init("pitch of ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPitch()";
    }
}