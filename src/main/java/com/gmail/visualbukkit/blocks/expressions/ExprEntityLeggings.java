package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@Description("The leggings of a living entity")
public class ExprEntityLeggings extends ExpressionBlock<ItemStack> {

    public ExprEntityLeggings() {
        init("leggings of ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().getLeggings()";
    }
}