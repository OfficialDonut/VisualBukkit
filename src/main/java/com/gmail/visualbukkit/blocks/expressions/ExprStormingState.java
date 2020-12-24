package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

@Description("The storming state in a world")
public class ExprStormingState extends ExpressionBlock<Boolean> {

    public ExprStormingState() {
        init("storming state in ", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".hasStorm()";
    }
}