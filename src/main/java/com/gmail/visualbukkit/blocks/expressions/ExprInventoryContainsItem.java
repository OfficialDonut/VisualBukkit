package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Description("Checks if an inventory contains an item")
public class ExprInventoryContainsItem extends ExpressionBlock<Boolean> {

    public ExprInventoryContainsItem() {
        init(Inventory.class, " contains at least ", int.class, " ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".containsAtLeast(" + arg(2) + "," + arg(1) + ")";
    }
}