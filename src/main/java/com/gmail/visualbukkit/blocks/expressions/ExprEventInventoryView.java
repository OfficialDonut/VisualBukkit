package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.InventoryView;

@Description("The inventory view in an InventoryEvent")
public class ExprEventInventoryView extends ExpressionBlock<InventoryView> {

    public ExprEventInventoryView() {
        init("inventory view");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Inventory view must be used in an InventoryEvent", InventoryEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getView()";
    }
}