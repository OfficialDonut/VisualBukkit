package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

@Description("The value of a player statistic")
public class ExprStatisticValue extends ExpressionBlock<Integer> {

    public ExprStatisticValue() {
        init("value of ", Statistic.class, " for ", OfflinePlayer.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getStatistic(" + arg(0) + ")";
    }
}
