package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.ItemStack;

@Description("The name of an item")
public class ExprItemName extends ExpressionBlock<String> {

    public ExprItemName() {
        init("name of ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getItemMeta().getDisplayName()";
    }
}