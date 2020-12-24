package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

@Description("The hand used in a PlayerInteractEvent")
public class ExprEventHand extends ExpressionBlock<EquipmentSlot> {

    public ExprEventHand() {
        init("event hand");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Event hand must be used in a PlayerInteractEvent", PlayerInteractEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getHand()";
    }
}