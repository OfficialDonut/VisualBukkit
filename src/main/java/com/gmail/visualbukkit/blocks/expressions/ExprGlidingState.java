package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;

@Description("The gliding state of a living entity")
public class ExprGlidingState extends ExpressionBlock<Boolean> {

    public ExprGlidingState() {
        init("gliding state of ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isGliding()";
    }
}
