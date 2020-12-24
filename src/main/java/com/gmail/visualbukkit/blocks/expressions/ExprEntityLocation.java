package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

@Description("The location of an entity")
public class ExprEntityLocation extends ExpressionBlock<Location> {

    public ExprEntityLocation() {
        init("location of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getLocation()";
    }
}