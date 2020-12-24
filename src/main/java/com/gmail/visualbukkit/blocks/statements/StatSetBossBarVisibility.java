package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BossBar;

@Description("Sets the visibility state of a boss bar")
public class StatSetBossBarVisibility extends StatementBlock {

    public StatSetBossBarVisibility() {
        init("set visibility state of ", BossBar.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setVisible(" + arg(1) + ");";
    }
}
