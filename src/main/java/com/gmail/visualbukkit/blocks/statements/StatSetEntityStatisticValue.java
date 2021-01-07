package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

@Description("Sets the value of a statistic")
public class StatSetEntityStatisticValue extends StatementBlock {

    public StatSetEntityStatisticValue() {
        init("set statistic value");
        initLine("player:      ", OfflinePlayer.class);
        initLine("entity type: ", EntityType.class);
        initLine("statistic:   ", Statistic.class);
        initLine("value:       ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setStatistic(" + arg(2) + "," + arg(1) + "," + arg(3) + ");";
    }
}
