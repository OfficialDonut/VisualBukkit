package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("Checks if an entity is on the ground")
public class ExprIsEntityOnGround extends ExpressionBlock<Boolean> {

    public ExprIsEntityOnGround() {
        init(Entity.class, " is on the ground");
    }

    @Override
    public String toJava() {
        return arg(0) + ".isOnGround()";
    }
}