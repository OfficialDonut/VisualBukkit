package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The lore of an item")
@SuppressWarnings("rawtypes")
public class ExprItemLore extends ExpressionBlock<List> {

    public ExprItemLore() {
        init("lore of ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getItemMeta().getLore()";
    }
}