package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Tameable;

@Description("The owner of a tameable entity")
public class ExprTamedOwner extends ExpressionBlock<AnimalTamer> {

    public ExprTamedOwner() {
        init("owner of ", Tameable.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getOwner()";
    }
}