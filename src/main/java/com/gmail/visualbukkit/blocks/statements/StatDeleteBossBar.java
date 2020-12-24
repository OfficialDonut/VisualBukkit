package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BossBar;

@Description("Removes a boss bar from all players")
public class StatDeleteBossBar extends StatementBlock {

    public StatDeleteBossBar() {
        init("delete ", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".removeAll();";
    }
}
