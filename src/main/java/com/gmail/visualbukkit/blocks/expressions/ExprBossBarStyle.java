package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

@Description("The style of a boss bar")
public class ExprBossBarStyle extends ExpressionBlock<BarStyle> {

    public ExprBossBarStyle() {
        init("style of ", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getStyle()";
    }
}