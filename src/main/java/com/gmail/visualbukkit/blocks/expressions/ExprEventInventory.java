package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;

@Description("The inventory in an InventoryEvent")
public class ExprEventInventory extends ExpressionBlock<Inventory> {

    public ExprEventInventory() {
        init("event inventory");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Event inventory must be used in an InventoryEvent", InventoryEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getInventory()";
    }
}