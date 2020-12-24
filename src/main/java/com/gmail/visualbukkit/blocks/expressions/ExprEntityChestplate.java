package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@Description("The chestplate of a living entity")
public class ExprEntityChestplate extends ExpressionBlock<ItemStack> {

    public ExprEntityChestplate() {
        init("chestplate of ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().getChestplate()";
    }
}