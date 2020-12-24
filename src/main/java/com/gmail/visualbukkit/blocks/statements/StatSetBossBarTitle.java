package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BossBar;

@Description("Sets the title of a boss bar")
public class StatSetBossBarTitle extends StatementBlock {

    public StatSetBossBarTitle() {
        init("set title of ", BossBar.class, " to ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setTitle(" + arg(1) + ");";
    }
}
