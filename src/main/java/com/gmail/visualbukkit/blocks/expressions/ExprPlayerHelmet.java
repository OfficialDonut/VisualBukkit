package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Description("The helmet of a player")
public class ExprPlayerHelmet extends ExpressionBlock<ItemStack> {

    public ExprPlayerHelmet() {
        init("helmet of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().getHelmet()";
    }
}