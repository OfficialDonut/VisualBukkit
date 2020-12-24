package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("Checks if an entity is dead")
public class ExprIsEntityDead extends ExpressionBlock<Boolean> {

    public ExprIsEntityDead() {
        init(Entity.class, " is dead");
    }

    @Override
    public String toJava() {
        return arg(0) + ".isDead()";
    }
}