package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Scoreboard;

@Description("A new scoreboard")
public class ExprNewScoreboard extends ExpressionBlock<Scoreboard> {

    public ExprNewScoreboard() {
        init("new scoreboard");
    }

    @Override
    public String toJava() {
        return "Bukkit.getScoreboardManager().getNewScoreboard()";
    }
}