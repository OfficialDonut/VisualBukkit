package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The flying state of a player")
public class ExprFlyingState extends ExpressionBlock<Boolean> {

    public ExprFlyingState() {
        init("flying state of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".isFlying()";
    }
}