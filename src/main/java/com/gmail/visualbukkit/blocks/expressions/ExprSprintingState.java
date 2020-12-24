package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The sprinting state of a player")
public class ExprSprintingState extends ExpressionBlock<Boolean> {

    public ExprSprintingState() {
        init("sprinting state of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".isSprinting()";
    }
}