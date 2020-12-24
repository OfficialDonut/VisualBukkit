package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;

@Description("The first empty slot in an inventory (-1 if full)")
public class ExprFirstEmptySlot extends ExpressionBlock<Integer> {

    public ExprFirstEmptySlot() {
        init("first empty slot in ", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".firstEmpty()";
    }
}