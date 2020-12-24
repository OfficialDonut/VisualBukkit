package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Team;

@Description("Adds an entry to a scoreboard team")
public class StatAddTeamEntry extends StatementBlock {

    public StatAddTeamEntry() {
        init("add entry ", String.class, " to ", Team.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".addEntry(PluginMain.color(" + arg(0) + "));";
    }
}
