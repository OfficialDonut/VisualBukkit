package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Objective;

@Description("The display name of a scoreboard objective")
public class ExprObjectiveDisplayName extends ExpressionBlock<String> {

    public ExprObjectiveDisplayName() {
        init("display name of ", Objective.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getDisplayName()";
    }
}