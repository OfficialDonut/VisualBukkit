package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The name of a player")
public class ExprPlayerName extends ExpressionBlock<String> {

    public ExprPlayerName() {
        init("name of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getName()";
    }
}
