package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@Description("The type of an inventory")
public class ExprTypeOfInventory extends ExpressionBlock<InventoryType> {

    public ExprTypeOfInventory() {
        init("type of ", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getType()";
    }
}