package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Description("The leggings of a player")
public class ExprPlayerLeggings extends ExpressionBlock<ItemStack> {

    public ExprPlayerLeggings() {
        init("leggings of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().getLeggings()";
    }
}