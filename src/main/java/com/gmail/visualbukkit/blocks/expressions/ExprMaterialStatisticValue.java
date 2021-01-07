package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

@Description("The value of a player statistic")
public class ExprMaterialStatisticValue extends ExpressionBlock<Integer> {

    public ExprMaterialStatisticValue() {
        init("value of ", Material.class, " ", Statistic.class, " for ", OfflinePlayer.class);
    }

    @Override
    public String toJava() {
        return arg(2) + ".getStatistic(" + arg(1) + "," + arg(0) + ")";
    }
}
