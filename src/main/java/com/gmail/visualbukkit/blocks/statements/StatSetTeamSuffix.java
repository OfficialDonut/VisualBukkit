package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Team;

@Description("Sets the suffix of a scoreboard team")
public class StatSetTeamSuffix extends StatementBlock {

    public StatSetTeamSuffix() {
        init("set suffix of ", Team.class, " to ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setSuffix(PluginMain.color(" + arg(1) + "));";
    }
}
