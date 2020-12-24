package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

@Description("The helmet of a living entity")
public class ExprEntityHelmet extends ExpressionBlock<ItemStack> {

    public ExprEntityHelmet() {
        init("helmet of ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().getHelmet()";
    }
}