package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

@Category(Category.WORLD)
@Description("Sets the time in a world")
public class StatSetWorldTime extends StatementBlock {

    public StatSetWorldTime() {
        init("set time in ", World.class, " to ", long.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setTime(" + arg(1) + ");";
    }
}
