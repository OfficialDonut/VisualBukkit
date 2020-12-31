package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;

@Description("Checks if a living entity is sleeping")
public class ExprIsEntitySleeping extends ExpressionBlock<Boolean> {

    public ExprIsEntitySleeping() {
        init(LivingEntity.class, " is sleeping");
    }

    @Override
    public String toJava() {
        return arg(0) + ".isSleeping()";
    }
}
