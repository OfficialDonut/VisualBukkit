package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

@Category(Category.PLAYER)
@Description("Sets the scoreboard of a player")
public class StatSetPlayerScoreboard extends StatementBlock {

    public StatSetPlayerScoreboard() {
        init("set scoreboard of ", Player.class, " to ", Scoreboard.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setScoreboard(" + arg(1) + ");";
    }
}
