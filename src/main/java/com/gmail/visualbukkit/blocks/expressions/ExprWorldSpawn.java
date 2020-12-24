package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.World;

@Description("The spawn location of a world")
public class ExprWorldSpawn extends ExpressionBlock<Location> {

    public ExprWorldSpawn() {
        init("spawn location of ", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSpawnLocation()";
    }
}