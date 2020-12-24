package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

@Description("Sets the style of a boss bar")
public class StatSetBossBarStyle extends StatementBlock {

    public StatSetBossBarStyle() {
        init("set style of ", BossBar.class, " to ", BarStyle.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setStyle(" + arg(1) + ");";
    }
}
