package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Damageable;

@Description("The health of a living entity")
public class ExprHealth extends ExpressionBlock<Double> {

    public ExprHealth() {
        init("health of ", Damageable.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getHealth()";
    }
}