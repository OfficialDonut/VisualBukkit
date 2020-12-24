package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.EntityDamageEvent;

@Description("The final damage amount in an EntityDamageEvent")
public class ExprEventFinalDamage extends ExpressionBlock<Double> {

    public ExprEventFinalDamage() {
        init("final damage");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Final damage must be used in an EntityDamageEvent", EntityDamageEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getFinalDamage()";
    }
}