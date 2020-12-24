package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;

@Description("The size of an inventory")
public class ExprInventorySize extends ExpressionBlock<Integer> {

    public ExprInventorySize() {
        init("size of ", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSize()";
    }
}