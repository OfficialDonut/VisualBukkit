package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

@Description("Removes a boss bar from a player")
public class StatRemoveBossBar extends StatementBlock {

    public StatRemoveBossBar() {
        init("remove ", BossBar.class, " from ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".removePlayer(" + arg(1) + ");";
    }
}
