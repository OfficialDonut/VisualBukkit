package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BossBar;

@Description("The progress of a boss bar")
public class ExprBossBarProgress extends ExpressionBlock<Double> {

    public ExprBossBarProgress() {
        init("progress of ", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getProgress()";
    }
}