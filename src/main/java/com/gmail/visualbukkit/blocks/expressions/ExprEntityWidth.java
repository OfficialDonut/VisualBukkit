package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("The width of an entity")
public class ExprEntityWidth extends ExpressionBlock<Double> {

    public ExprEntityWidth() {
        init("width of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWidth()";
    }
}