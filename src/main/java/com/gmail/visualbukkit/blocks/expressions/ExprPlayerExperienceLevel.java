package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The experience level of a player")
public class ExprPlayerExperienceLevel extends ExpressionBlock<Integer> {

    public ExprPlayerExperienceLevel() {
        init("experience level of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getLevel()";
    }
}