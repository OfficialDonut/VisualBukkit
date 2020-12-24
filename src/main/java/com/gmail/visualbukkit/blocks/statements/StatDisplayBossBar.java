package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

@Description("Displays a boss bar to a player")
public class StatDisplayBossBar extends StatementBlock {

    public StatDisplayBossBar() {
        init("display ", BossBar.class, " to ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".addPlayer(" + arg(1) + ");";
    }
}
