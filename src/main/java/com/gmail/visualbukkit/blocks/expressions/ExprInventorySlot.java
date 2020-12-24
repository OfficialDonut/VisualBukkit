package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Description("The item in a slot of an inventory")
public class ExprInventorySlot extends ExpressionBlock<ItemStack> {

    public ExprInventorySlot() {
        init("slot ", int.class, " of ", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getItem(" + arg(0) + ")";
    }
}