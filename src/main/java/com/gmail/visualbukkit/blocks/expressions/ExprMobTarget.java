package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

@Description("The target of a mob")
public class ExprMobTarget extends ExpressionBlock<LivingEntity> {

    public ExprMobTarget() {
        init("target of ", Mob.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getTarget()";
    }
}