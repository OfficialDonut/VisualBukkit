package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Description("The bed location of a player")
public class ExprBedLocation extends ExpressionBlock<Location> {

    public ExprBedLocation() {
        init("bed location of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getBedSpawnLocation()";
    }
}