package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

@Description("The velocity of an entity")
public class ExprEntityVelocity extends ExpressionBlock<Vector> {

    public ExprEntityVelocity() {
        init("velocity of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getVelocity()";
    }
}