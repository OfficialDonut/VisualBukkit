package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("The max fire ticks of an entity")
public class ExprMaxFireTicks extends ExpressionBlock<Integer> {

    public ExprMaxFireTicks() {
        init("max fire ticks of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getMaxFireTicks()";
    }
}