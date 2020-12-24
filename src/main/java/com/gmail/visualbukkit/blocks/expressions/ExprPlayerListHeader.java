package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The player list header for a player")
public class ExprPlayerListHeader extends ExpressionBlock<String> {

    public ExprPlayerListHeader() {
        init("player list header of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPlayerListHeader()";
    }
}