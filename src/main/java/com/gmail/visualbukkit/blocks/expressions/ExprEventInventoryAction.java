package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

@Description("The inventory action in an InventoryClickEvent")
public class ExprEventInventoryAction extends ExpressionBlock<InventoryAction> {

    public ExprEventInventoryAction() {
        init("inventory action");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Inventory action must be used in an InventoryClickEvent", InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getAction()";
    }
}