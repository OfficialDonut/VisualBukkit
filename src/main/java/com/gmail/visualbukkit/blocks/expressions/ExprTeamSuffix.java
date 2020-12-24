package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.scoreboard.Team;

@Description("The suffix of a scoreboard team")
public class ExprTeamSuffix extends ExpressionBlock<String> {

    public ExprTeamSuffix() {
        init("suffix of ", Team.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSuffix()";
    }
}