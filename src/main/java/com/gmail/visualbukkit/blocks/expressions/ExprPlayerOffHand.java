package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Description("The off hand item of a player")
public class ExprPlayerOffHand extends ExpressionBlock<ItemStack> {

    public ExprPlayerOffHand() {
        init("off hand item of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().getItemInOffHand()";
    }
}