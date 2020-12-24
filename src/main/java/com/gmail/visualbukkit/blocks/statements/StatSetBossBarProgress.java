package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BossBar;

@Description("Sets the progress of a boss bar")
public class StatSetBossBarProgress extends StatementBlock {

    public StatSetBossBarProgress() {
        init("set progress of ", BossBar.class, " to ", double.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setProgress(" + arg(1) + ");";
    }
}
