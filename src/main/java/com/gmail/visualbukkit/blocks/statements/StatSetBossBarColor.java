package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;

@Description("Sets the color of a boss bar")
public class StatSetBossBarColor extends StatementBlock {

    public StatSetBossBarColor() {
        init("set color of ", BossBar.class, " to ", BarColor.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setColor(" + arg(1) + ");";
    }
}
