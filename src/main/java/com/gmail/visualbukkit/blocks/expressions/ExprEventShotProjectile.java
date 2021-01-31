package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityShootBowEvent;

@Description("The projectile entity shot in an EntityShootBowEvent")
public class ExprEventShotProjectile extends ExpressionBlock<Entity> {

    public ExprEventShotProjectile() {
        init("shot projectile");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Shot projectile must be used in an EntityShootBowEvent", EntityShootBowEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getProjectile()";
    }
}
