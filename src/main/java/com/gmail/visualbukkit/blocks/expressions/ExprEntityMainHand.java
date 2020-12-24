package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@Description("The main hand item of a living entity")
public class ExprEntityMainHand extends ExpressionBlock<ItemStack> {

    public ExprEntityMainHand() {
        init("main hand item of ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().getItemInMainHand()";
    }
}