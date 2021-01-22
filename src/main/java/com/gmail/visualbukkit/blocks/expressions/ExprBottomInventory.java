package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

@Description("The bottom inventory of an inventory view")
public class ExprBottomInventory extends ExpressionBlock<Inventory> {

    public ExprBottomInventory() {
        init("bottom inventory of ", InventoryView.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getBottomInventory()";
    }
}
