package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The walk speed of a player")
public class ExprWalkSpeed extends ExpressionBlock<Float> {

    public ExprWalkSpeed() {
        init("walk speed of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWalkSpeed()";
    }
}