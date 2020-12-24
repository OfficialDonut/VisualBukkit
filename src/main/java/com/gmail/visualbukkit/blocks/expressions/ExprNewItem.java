package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Description("An item")
public class ExprNewItem extends ExpressionBlock<ItemStack> {

    public ExprNewItem() {
        init("item of ", Material.class);
    }

    @Override
    public String toJava() {
        return "new ItemStack(" + arg(0) + ")";
    }
}