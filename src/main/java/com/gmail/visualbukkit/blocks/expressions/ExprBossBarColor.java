package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;

@Description("The color of a boss bar")
public class ExprBossBarColor extends ExpressionBlock<BarColor> {

    public ExprBossBarColor() {
        init("color of ", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getColor()";
    }
}