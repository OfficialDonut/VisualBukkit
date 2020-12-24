package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Objective;

@Description("Sets the display name of a scoreboard objective")
public class StatSetObjectiveDisplayName extends StatementBlock {

    public StatSetObjectiveDisplayName() {
        init("set display name of ", Objective.class, " to ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setDisplayName(PluginMain.color(" + arg(1) + "));";
    }
}
