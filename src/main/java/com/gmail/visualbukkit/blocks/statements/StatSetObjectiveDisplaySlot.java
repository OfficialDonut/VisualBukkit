package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

@Description("Sets the display slot of a scoreboard objective")
public class StatSetObjectiveDisplaySlot extends StatementBlock {

    public StatSetObjectiveDisplaySlot() {
        init("set display slot of ", Objective.class, " to ", DisplaySlot.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setDisplaySlot(" + arg(1) + ");";
    }
}
