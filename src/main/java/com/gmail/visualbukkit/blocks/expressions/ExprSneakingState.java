package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The sneaking state of a player")
public class ExprSneakingState extends ExpressionBlock<Boolean> {

    public ExprSneakingState() {
        init("sneaking state of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".isSneaking()";
    }
}