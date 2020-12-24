package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.ItemStack;

@Description("The durability damage of an item")
public class ExprItemDurability extends ExpressionBlock<Integer> {

    public ExprItemDurability() {
        init("durability damage of ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "((org.bukkit.inventory.meta.Damageable)" + arg(0) + ".getItemMeta()).getDamage()";
    }
}