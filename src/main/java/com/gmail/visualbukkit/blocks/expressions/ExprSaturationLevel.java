package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The saturation level of a player")
public class ExprSaturationLevel extends ExpressionBlock<Float> {

    public ExprSaturationLevel() {
        init("saturation level of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSaturation()";
    }
}