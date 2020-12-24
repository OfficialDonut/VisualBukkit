package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Description("The boots of a player")
public class ExprPlayerBoots extends ExpressionBlock<ItemStack> {

    public ExprPlayerBoots() {
        init("boots of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().getBoots()";
    }
}