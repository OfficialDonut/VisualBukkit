package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The food level of a player")
public class ExprFoodLevel extends ExpressionBlock<Integer> {

    public ExprFoodLevel() {
        init("food level of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getFoodLevel()";
    }
}