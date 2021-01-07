package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

@Description("Sets the value of a statistic")
public class StatSetMaterialStatisticValue extends StatementBlock {

    public StatSetMaterialStatisticValue() {
        init("set statistic value");
        initLine("player:    ", OfflinePlayer.class);
        initLine("material:  ", Material.class);
        initLine("statistic: ", Statistic.class);
        initLine("value:     ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setStatistic(" + arg(2) + "," + arg(1) + "," + arg(3) + ");";
    }
}
