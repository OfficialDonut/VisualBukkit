package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.World;

@Category(Category.WORLD)
@Description("Sets the spawn location of a world")
public class StatSetWorldSpawn extends StatementBlock {

    public StatSetWorldSpawn() {
        init("set spawn location of ", World.class, " to ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setSpawnLocation(" + arg(1) + ");";
    }
}
