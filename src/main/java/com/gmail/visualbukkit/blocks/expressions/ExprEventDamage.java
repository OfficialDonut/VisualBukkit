package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.EntityDamageEvent;

@Description("The damage amount in an EntityDamageEvent")
public class ExprEventDamage extends ExpressionBlock<Double> {

    public ExprEventDamage() {
        init("damage");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Damage must be used in an EntityDamageEvent", EntityDamageEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getDamage()";
    }
}