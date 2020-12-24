package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

@Category(Category.WORLD)
@Description("Sets the storming state in a world")
public class StatSetStormingState extends StatementBlock {

    public StatSetStormingState() {
        init("set storming state in ", World.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setStorm(" + arg(1) + ");";
    }
}
