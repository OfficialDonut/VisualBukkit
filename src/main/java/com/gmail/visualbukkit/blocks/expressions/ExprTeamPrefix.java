package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Team;

@Description("The prefix of a scoreboard team")
public class ExprTeamPrefix extends ExpressionBlock<String> {

    public ExprTeamPrefix() {
        init("prefix of ", Team.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPrefix()";
    }
}