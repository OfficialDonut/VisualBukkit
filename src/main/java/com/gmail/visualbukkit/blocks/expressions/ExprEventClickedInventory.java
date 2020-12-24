package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

@Description("The clicked inventory in an InventoryClickEvent")
public class ExprEventClickedInventory extends ExpressionBlock<Inventory> {

    public ExprEventClickedInventory() {
        init("clicked inventory");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Clicked inventory must be used in an InventoryClickEvent", InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getClickedInventory()";
    }
}