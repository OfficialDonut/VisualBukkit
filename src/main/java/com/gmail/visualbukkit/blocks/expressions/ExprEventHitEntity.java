package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.ProjectileHitEvent;

@Description("The entity that was hit in a ProjectileHitEvent")
public class ExprEventHitEntity extends ExpressionBlock<Entity> {

    public ExprEventHitEntity() {
        init("hit entity");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Hit entity must be used in a ProjectileHitEvent", ProjectileHitEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getHitEntity()";
    }
}