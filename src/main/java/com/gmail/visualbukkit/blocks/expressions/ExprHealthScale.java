package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The health scale of a player")
public class ExprHealthScale extends ExpressionBlock<Double> {

    public ExprHealthScale() {
        init("health scale of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getHealthScale()";
    }
}