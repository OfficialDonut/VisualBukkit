package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.EntityDamageEvent;

@Description("The damage cause in an EntityDamageEvent")
public class ExprEventDamageCause extends ExpressionBlock<EntityDamageEvent.DamageCause> {

    public ExprEventDamageCause() {
        init("damage cause");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Damage cause must be used in an EntityDamageEvent", EntityDamageEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getCause()";
    }
}