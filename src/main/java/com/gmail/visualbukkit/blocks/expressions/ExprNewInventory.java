package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@Description("A new inventory")
public class ExprNewInventory extends ExpressionBlock<Inventory> {

    public ExprNewInventory() {
        init("new ", InventoryType.class, " inventory named ", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.createInventory(null," + arg(0) + ",PluginMain.color(" + arg(1) + "))";
    }
}