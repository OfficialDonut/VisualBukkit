package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@Description("The off hand item of a living entity")
public class ExprEntityOffHand extends ExpressionBlock<ItemStack> {

    public ExprEntityOffHand() {
        init("off hand item of ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().getItemInOffHand()";
    }
}