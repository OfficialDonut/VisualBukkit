package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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