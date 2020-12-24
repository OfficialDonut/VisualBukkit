package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

@Category(Category.WORLD)
@Description("Sets the remaining time in ticks of the current weather conditions")
public class StatSetWeatherDuration extends StatementBlock {

    public StatSetWeatherDuration() {
        init("set weather duration in ", World.class, " to ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setWeatherDuration(" + arg(1) + ");";
    }
}
