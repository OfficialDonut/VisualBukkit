package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("The vehicle of an entity")
public class ExprEntityVehicle extends ExpressionBlock<Entity> {

    public ExprEntityVehicle() {
        init("vehicle of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getVehicle()";
    }
}