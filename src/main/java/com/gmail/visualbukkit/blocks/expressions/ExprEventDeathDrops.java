package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

@Description("The items dropped in an EntityDeathEvent (mutable list)")
@SuppressWarnings("rawtypes")
public class ExprEventDeathDrops extends ExpressionBlock<List> {

    public ExprEventDeathDrops() {
        init("death drops");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Death drops must be used in an EntityDeathEvent", EntityDeathEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getDrops()";
    }
}