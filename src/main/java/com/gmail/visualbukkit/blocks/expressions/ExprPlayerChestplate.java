package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Description("The chestplate of a player")
public class ExprPlayerChestplate extends ExpressionBlock<ItemStack> {

    public ExprPlayerChestplate() {
        init("chestplate of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().getChestplate()";
    }
}