package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Team;

@Description("Removes an entry from a scoreboard team")
public class StatRemoveTeamEntry extends StatementBlock {

    public StatRemoveTeamEntry() {
        init("remove entry ", String.class, " from ", Team.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".removeEntry(PluginMain.color(" + arg(0) + "));";
    }
}
