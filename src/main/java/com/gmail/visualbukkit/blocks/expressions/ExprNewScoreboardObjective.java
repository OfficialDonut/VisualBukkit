package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

@Description("Registers a new scoreboard objective")
public class ExprNewScoreboardObjective extends ExpressionBlock<Objective> {

    public ExprNewScoreboardObjective() {
        init("new objective named ", String.class, " with criteria ", String.class, " for ", Scoreboard.class);
    }

    @Override
    public String toJava() {
        return arg(2) + ".registerNewObjective(" + arg(0) + "," + arg(1) + ")";
    }
}