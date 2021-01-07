package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

@Description("The item associated with a dropped item entity")
public class ExprDroppedItem extends ExpressionBlock<ItemStack> {

    public ExprDroppedItem() {
        init("item of ", Item.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getItemStack()";
    }
}
