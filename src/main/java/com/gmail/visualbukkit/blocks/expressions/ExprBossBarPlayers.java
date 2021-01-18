package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BossBar;

import java.util.List;

@SuppressWarnings("rawtypes")
@Description("The players viewing a boss bar")
public class ExprBossBarPlayers extends ExpressionBlock<List> {

    public ExprBossBarPlayers() {
        init("players viewing ", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPlayers()";
    }
}