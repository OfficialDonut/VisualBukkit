package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@Description("The damager in an EntityDamageByEntityEvent")
public class ExprEventDamager extends ExpressionBlock<Entity> {

    public ExprEventDamager() {
        init("damager");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Damager must be used in an EntityDamageByEntityEvent", EntityDamageByEntityEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getDamager()";
    }
}