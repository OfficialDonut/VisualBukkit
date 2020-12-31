package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerBedEnterEvent;

@Description("The result in a PlayerBedEnterEvent")
public class ExprEventBedEnterResult extends ExpressionBlock<PlayerBedEnterEvent.BedEnterResult> {

    public ExprEventBedEnterResult() {
        init("bed enter result");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Bed enter result must be used in a PlayerBedEnterEvent", PlayerBedEnterEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getBedEnterResult()";
    }
}
