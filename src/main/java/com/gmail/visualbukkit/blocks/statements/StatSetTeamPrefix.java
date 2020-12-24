package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Team;

@Description("Sets the prefix of a scoreboard team")
public class StatSetTeamPrefix extends StatementBlock {

    public StatSetTeamPrefix() {
        init("set prefix of ", Team.class, " to ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setPrefix(PluginMain.color(" + arg(1) + "));";
    }
}
