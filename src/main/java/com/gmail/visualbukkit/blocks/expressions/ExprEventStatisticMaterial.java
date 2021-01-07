package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import javafx.scene.paint.Material;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

@Description("The material of the statistic in a PlayerStatisticIncrementEvent")
public class ExprEventStatisticMaterial extends ExpressionBlock<Material> {

    public ExprEventStatisticMaterial() {
        init("statistic material");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Statistic material must be used in a PlayerStatisticIncrementEvent", PlayerStatisticIncrementEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getMaterial()";
    }
}
