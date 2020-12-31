package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;

@Description("The swimming state of a living entity")
public class ExprSwimmingState extends ExpressionBlock<Boolean> {

    public ExprSwimmingState() {
        init("swimming state of ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isSwimming()";
    }
}
