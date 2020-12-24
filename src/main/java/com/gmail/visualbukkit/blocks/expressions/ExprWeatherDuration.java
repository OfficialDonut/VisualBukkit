package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

@Description("The remaining time in ticks of the current weather conditions")
public class ExprWeatherDuration extends ExpressionBlock<Integer> {

    public ExprWeatherDuration() {
        init("weather duration in ", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWeatherDuration()";
    }
}