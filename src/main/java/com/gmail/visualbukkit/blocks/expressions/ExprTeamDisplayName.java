package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Team;

@Description("The display name of a scoreboard team")
public class ExprTeamDisplayName extends ExpressionBlock<String> {

    public ExprTeamDisplayName() {
        init("display name of ", Team.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getDisplayName()";
    }
}