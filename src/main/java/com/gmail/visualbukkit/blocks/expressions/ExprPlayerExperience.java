package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The experience points of a player")
public class ExprPlayerExperience extends ExpressionBlock<Float> {

    public ExprPlayerExperience() {
        init("experience points of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getExp()";
    }
}