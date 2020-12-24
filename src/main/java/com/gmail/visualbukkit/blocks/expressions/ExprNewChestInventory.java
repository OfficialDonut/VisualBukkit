package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;

@Description("A new inventory")
public class ExprNewChestInventory extends ExpressionBlock<Inventory> {

    public ExprNewChestInventory() {
        init("new chest inventory named ", String.class, " with size ", int.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.createInventory(null," + arg(1) + ",PluginMain.color(" + arg(0) + "))";
    }
}