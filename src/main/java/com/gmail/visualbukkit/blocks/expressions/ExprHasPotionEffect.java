package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

@Description("Checks if a living entity has a potion effect")
public class ExprHasPotionEffect extends ExpressionBlock<Boolean> {

    public ExprHasPotionEffect() {
        init(LivingEntity.class, " has ", PotionEffectType.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".hasPotionEffect(" + arg(1) + ")";
    }
}