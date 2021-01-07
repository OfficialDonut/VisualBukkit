package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

@Description("The entity type of the statistic in a PlayerStatisticIncrementEvent")
public class ExprEventStatisticEntityType extends ExpressionBlock<EntityType> {

    public ExprEventStatisticEntityType() {
        init("statistic entity type");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Statistic entity type must be used in a PlayerStatisticIncrementEvent", PlayerStatisticIncrementEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getEntityType()";
    }
}
