package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@Description("The boots of a living entity")
public class ExprEntityBoots extends ExpressionBlock<ItemStack> {

    public ExprEntityBoots() {
        init("boots of ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().getBoots()";
    }
}