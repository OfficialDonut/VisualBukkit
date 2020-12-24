package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BossBar;

@Description("Creates a new boss bar")
public class ExprNewBossBar extends ExpressionBlock<BossBar> {

    public ExprNewBossBar() {
        init("new boss bar with title ", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.createBossBar(" + arg(0) + ", org.bukkit.boss.BarColor.BLUE, org.bukkit.boss.BarStyle.SOLID)";
    }
}