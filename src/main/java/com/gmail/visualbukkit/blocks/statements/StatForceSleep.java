package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Forces a player to sleep in a bed")
public class StatForceSleep extends StatementBlock {

    public StatForceSleep() {
        init("force ", Player.class, " to sleep in bed at ", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".sleep(" + arg(1) + ",true);";
    }
}
