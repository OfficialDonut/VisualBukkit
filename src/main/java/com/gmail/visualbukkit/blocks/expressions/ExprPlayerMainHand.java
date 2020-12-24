package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Description("The item in the main hand of a player")
public class ExprPlayerMainHand extends ExpressionBlock<ItemStack> {

    public ExprPlayerMainHand() {
        init("item in main hand of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().getItemInMainHand()";
    }
}