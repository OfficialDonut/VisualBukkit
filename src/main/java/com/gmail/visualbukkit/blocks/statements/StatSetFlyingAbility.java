package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the flying ability of a player")
public class StatSetFlyingAbility extends StatementBlock {

    public StatSetFlyingAbility() {
        init("set flying ability of ", Player.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setAllowFlight(" + arg(1) + ");";
    }
}
