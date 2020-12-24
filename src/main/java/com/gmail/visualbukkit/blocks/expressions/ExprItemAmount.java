package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.ItemStack;

@Description("The amount of an item")
public class ExprItemAmount extends ExpressionBlock<Integer> {

    public ExprItemAmount() {
        init("amount of ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getAmount()";
    }
}