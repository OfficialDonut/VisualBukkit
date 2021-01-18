package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import org.bukkit.scoreboard.Team;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The entries of a scoreboard team")
@SuppressWarnings("rawtypes")
public class ExprTeamEntries extends ExpressionBlock<List> {

    public ExprTeamEntries() {
        init("entries of ", Team.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getEntries())";
    }
}