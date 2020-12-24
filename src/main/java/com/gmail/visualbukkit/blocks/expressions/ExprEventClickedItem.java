package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Description("The clicked item in an InventoryClickEvent")
public class ExprEventClickedItem extends ExpressionBlock<ItemStack> {

    public ExprEventClickedItem() {
        init("clicked item");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Event clicked item must be used in an InventoryClickEvent", InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getCurrentItem()";
    }
}