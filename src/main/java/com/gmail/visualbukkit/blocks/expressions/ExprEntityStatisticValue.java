package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

@Description("The value of a player statistic")
public class ExprEntityStatisticValue extends ExpressionBlock<Integer> {

    public ExprEntityStatisticValue() {
        init("value of ", EntityType.class, " ", Statistic.class, " for ", OfflinePlayer.class);
    }

    @Override
    public String toJava() {
        return arg(2) + ".getStatistic(" + arg(1) + "," + arg(0) + ")";
    }
}
