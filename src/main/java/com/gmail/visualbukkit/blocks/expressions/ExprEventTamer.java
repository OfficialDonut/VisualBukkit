package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.event.entity.EntityTameEvent;

@Description("The tamer in an EntityTameEvent")
public class ExprEventTamer extends ExpressionBlock<AnimalTamer> {

    public ExprEventTamer() {
        init("tamer");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Tamer must be used in an EntityTameEvent", EntityTameEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getOwner()";
    }
}