package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

@Description("The new statistic value in a PlayerStatisticIncrementEvent")
public class ExprEventNewStatisticValue extends ExpressionBlock<Integer> {

    public ExprEventNewStatisticValue() {
        init("new statistic value");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("New statistic value must be used in a PlayerStatisticIncrementEvent", PlayerStatisticIncrementEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getNewValue()";
    }
}
