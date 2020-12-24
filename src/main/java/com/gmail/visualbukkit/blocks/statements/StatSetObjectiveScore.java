package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Objective;

@Description("Sets the score for an entry of a scoreboard objective")
public class StatSetObjectiveScore extends StatementBlock {

    public StatSetObjectiveScore() {
        init("set objective score");
        initLine("objective: ", Objective.class);
        initLine("entry:     ", String.class);
        initLine("score:     ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getScore(" + arg(1) + ").setScore(" + arg(2) + ");";
    }
}
