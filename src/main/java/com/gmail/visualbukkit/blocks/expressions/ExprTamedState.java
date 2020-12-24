package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Tameable;

@Description("The tamed state of a tameable entity")
public class ExprTamedState extends ExpressionBlock<Boolean> {

    public ExprTamedState() {
        init("tamed state of ", Tameable.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isTamed()";
    }
}