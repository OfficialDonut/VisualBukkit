package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.EntityDeathEvent;

@Description("The amount of experience dropped in an EntityDeathEvent")
public class ExprEventDroppedExperience extends ExpressionBlock<Integer> {

    public ExprEventDroppedExperience() {
        init("dropped experience");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Dropped experience must be used in an EntityDeathEvent", EntityDeathEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getDroppedExp()";
    }
}