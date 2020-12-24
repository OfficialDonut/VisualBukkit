package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

@Description("The display slot of a scoreboard objective")
public class ExprObjectiveDisplaySlot extends ExpressionBlock<DisplaySlot> {

    public ExprObjectiveDisplaySlot() {
        init("display slot of ", Objective.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getDisplaySlot()";
    }
}