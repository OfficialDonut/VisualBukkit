package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerItemDamageEvent;

@Description("The durability damage in a PlayerItemDamageEvent")
public class ExprEventDurabilityDamage extends ExpressionBlock<Integer> {

    public ExprEventDurabilityDamage() {
        init("durability damage");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Durability damage must be used in a PlayerItemDamageEvent", PlayerItemDamageEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getDamage()";
    }
}