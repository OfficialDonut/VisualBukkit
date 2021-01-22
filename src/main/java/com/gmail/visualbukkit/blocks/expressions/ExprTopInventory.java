package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

@Description("The top inventory of an inventory view")
public class ExprTopInventory extends ExpressionBlock<Inventory> {

    public ExprTopInventory() {
        init("top inventory of ", InventoryView.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getTopInventory()";
    }
}
