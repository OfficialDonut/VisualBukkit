package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

@Description("The click type in an InventoryClickEvent")
public class ExprEventInventoryClickType extends ExpressionBlock<ClickType> {

    public ExprEventInventoryClickType() {
        init("inventory click type");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Inventory click type must be used in an InventoryClickEvent", InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getClick()";
    }
}