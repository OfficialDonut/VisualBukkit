package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

@Description("The scoreboard of a player")
public class ExprPlayerScoreboard extends ExpressionBlock<Scoreboard> {

    public ExprPlayerScoreboard() {
        init("scoreboard of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getScoreboard()";
    }
}