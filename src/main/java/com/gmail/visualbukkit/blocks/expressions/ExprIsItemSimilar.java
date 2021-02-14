package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.ItemStack;

@Description("Checks if two items are the same ignoring stack size")
public class ExprIsItemSimilar extends ExpressionBlock<Boolean> {

    public ExprIsItemSimilar() {
        init(ItemStack.class, " is similar to ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isSimilar(" + arg(1) + ")";
    }
}
