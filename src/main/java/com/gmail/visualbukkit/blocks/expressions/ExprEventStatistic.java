package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Statistic;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

@Description("The statistic in a PlayerStatisticIncrementEvent")
public class ExprEventStatistic extends ExpressionBlock<Statistic> {

    public ExprEventStatistic() {
        init("event statistic");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Event statistic must be used in a PlayerStatisticIncrementEvent", PlayerStatisticIncrementEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getStatistic()";
    }
}
