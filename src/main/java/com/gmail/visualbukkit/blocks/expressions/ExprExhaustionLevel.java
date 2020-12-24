package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The exhaustion level of a player")
public class ExprExhaustionLevel extends ExpressionBlock<Float> {

    public ExprExhaustionLevel() {
        init("exhaustion level of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getExhaustion()";
    }
}