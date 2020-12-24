package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import org.bukkit.scoreboard.Team;

public class StatSetTeamDisplayName extends StatementBlock {

    public StatSetTeamDisplayName() {
        init("set display name of ", Team.class, " to ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setDisplayName(PluginMain.color(" + arg(1) + "));";
    }
}
