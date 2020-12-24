package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@Description("Registers a new scoreboard team")
public class ExprNewScoreboardTeam extends ExpressionBlock<Team> {

    public ExprNewScoreboardTeam() {
        init("new team named ", String.class, " for ", Scoreboard.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".registerNewTeam(" + arg(0) + ")";
    }
}