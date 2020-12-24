package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("The distance an entity has fallen")
public class ExprFallDistance extends ExpressionBlock<Float> {

    public ExprFallDistance() {
        init("fall distance of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getFallDistance()";
    }
}