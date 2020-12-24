package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.boss.BossBar;

@Description("The title of a boss bar")
public class ExprBossBarTitle extends ExpressionBlock<String> {

    public ExprBossBarTitle() {
        init("title of ", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getTitle()";
    }
}