package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.inventory.InventoryClickEvent;

@Description("The clicked slot in an InventoryClickEvent")
public class ExprEventClickedSlot extends ExpressionBlock<Integer> {

    public ExprEventClickedSlot() {
        init("clicked slot");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Clicked slot must be used in an InventoryClickEvent", InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getSlot()";
    }
}