package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

@Description("The previous statistic value in a PlayerStatisticIncrementEvent")
public class ExprEventPreviousStatisticValue extends ExpressionBlock<Integer> {

    public ExprEventPreviousStatisticValue() {
        init("previous statistic value");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Previous statistic value must be used in a PlayerStatisticIncrementEvent", PlayerStatisticIncrementEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getPreviousValue()";
    }
}
