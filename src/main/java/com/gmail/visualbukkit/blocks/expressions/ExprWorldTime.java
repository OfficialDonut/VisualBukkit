package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

@Description("The time in a world")
public class ExprWorldTime extends ExpressionBlock<Long> {

    public ExprWorldTime() {
        init("time in ", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getTime()";
    }
}