package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The fly speed of a player")
public class ExprFlySpeed extends ExpressionBlock<Float> {

    public ExprFlySpeed() {
        init("fly speed of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getFlySpeed()";
    }
}