package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;

@Description("The visibility state of a living entity")
public class ExprVisibilityState extends ExpressionBlock<Boolean> {

    public ExprVisibilityState() {
        init("visibility state of ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return "(!" + arg(0) + ".isInvisible())";
    }
}
