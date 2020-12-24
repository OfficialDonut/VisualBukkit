package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Description("Sets the compass target of a player")
public class StatSetCompassTarget extends StatementBlock {

    public StatSetCompassTarget() {
        init("set compass target of ", Player.class, " to ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setCompassTarget(" + arg(1) + ");";
    }
}
