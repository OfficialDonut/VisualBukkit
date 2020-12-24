package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("The number of ticks before an entity stops being on fire")
public class ExprEntityFireTicks extends ExpressionBlock<Integer> {

    public ExprEntityFireTicks() {
        init("fire ticks of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getFireTicks()";
    }
}