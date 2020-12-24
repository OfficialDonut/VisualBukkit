package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The operator status of a player")
public class ExprOperatorStatus extends ExpressionBlock<Boolean> {

    public ExprOperatorStatus() {
        init("operator status of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isOp()";
    }
}