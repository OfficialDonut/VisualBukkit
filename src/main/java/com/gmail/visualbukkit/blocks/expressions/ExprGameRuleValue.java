package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

@Description("The value of a game rule in a world")
public class ExprGameRuleValue extends ExpressionBlock<String> {

    public ExprGameRuleValue() {
        init("value of game rule ", String.class, " in ", World.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getGameRuleValue(" + arg(0) + ")";
    }
}
