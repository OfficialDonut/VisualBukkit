package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;

@Description("The gravity state of an entity")
public class ExprGravityState extends ExpressionBlock<Boolean> {

    public ExprGravityState() {
        init("gravity state of ", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".hasGravity()";
    }
}