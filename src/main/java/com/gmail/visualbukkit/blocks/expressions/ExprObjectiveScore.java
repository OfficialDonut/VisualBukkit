package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Objective;

@Description("The score of an entry for a scoreboard objective")
public class ExprObjectiveScore extends ExpressionBlock<Integer> {

    public ExprObjectiveScore() {
        init("score of entry ", String.class, " for ", Objective.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getScore(" + arg(0) + ").getScore()";
    }
}