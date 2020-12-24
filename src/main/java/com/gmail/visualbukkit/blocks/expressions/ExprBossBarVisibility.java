package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BossBar;

@Description("The visibility state of a boss bar")
public class ExprBossBarVisibility extends ExpressionBlock<Boolean> {

    public ExprBossBarVisibility() {
        init("visibility state of ", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isVisible()";
    }
}