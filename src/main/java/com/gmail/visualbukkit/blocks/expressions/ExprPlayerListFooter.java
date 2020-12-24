package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The player list footer for a player")
public class ExprPlayerListFooter extends ExpressionBlock<String> {

    public ExprPlayerListFooter() {
        init("player list footer of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPlayerListFooter()";
    }
}