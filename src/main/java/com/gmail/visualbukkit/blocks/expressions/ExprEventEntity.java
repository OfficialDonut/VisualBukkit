package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityEvent;

@Description("The entity in an EntityEvent")
public class ExprEventEntity extends ExpressionBlock<Entity> {

    public ExprEventEntity() {
        init("event entity");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Event entity must be used in an EntityEvent", EntityEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getEntity()";
    }
}