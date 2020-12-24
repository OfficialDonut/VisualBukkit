package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("The height of an entity")
public class ExprEntityHeight extends ExpressionBlock<Double> {

    public ExprEntityHeight() {
        init("height of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getHeight()";
    }
}