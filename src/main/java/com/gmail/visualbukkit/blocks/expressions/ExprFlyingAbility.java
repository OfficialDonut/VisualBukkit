package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The flying ability of a player")
public class ExprFlyingAbility extends ExpressionBlock<Boolean> {

    public ExprFlyingAbility() {
        init("flying ability of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".getAllowFlight()";
    }
}