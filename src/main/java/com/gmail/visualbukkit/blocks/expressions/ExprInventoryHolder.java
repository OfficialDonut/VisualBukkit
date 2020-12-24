package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Description("The block/entity to which an inventory belongs")
public class ExprInventoryHolder extends ExpressionBlock<InventoryHolder> {

    public ExprInventoryHolder() {
        init("holder of ", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getHolder()";
    }
}