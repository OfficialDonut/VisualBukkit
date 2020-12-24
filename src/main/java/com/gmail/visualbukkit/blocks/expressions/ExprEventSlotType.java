package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

@Description("The type of slot that was clicked in an InventoryClickEvent")
public class ExprEventSlotType extends ExpressionBlock<InventoryType.SlotType> {

    public ExprEventSlotType() {
        init("slot type");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Slot type must be used in an InventoryClickEvent", InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getSlotType()";
    }
}