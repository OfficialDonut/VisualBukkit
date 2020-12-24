package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("Checks if a player can see another player")
public class ExprCanSeePlayer extends ExpressionBlock<Boolean> {

    public ExprCanSeePlayer() {
        init(Player.class, " can see ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".canSee(" + arg(1) + ")";
    }
}