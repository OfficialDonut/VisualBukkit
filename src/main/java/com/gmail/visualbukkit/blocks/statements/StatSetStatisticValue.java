package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

@Description("Sets the value of a statistic")
public class StatSetStatisticValue extends StatementBlock {

    public StatSetStatisticValue() {
        init("set statistic value");
        initLine("player:    ", OfflinePlayer.class);
        initLine("statistic: ", Statistic.class);
        initLine("value:     ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setStatistic(" + arg(1) + "," + arg(2) + ");";
    }
}
