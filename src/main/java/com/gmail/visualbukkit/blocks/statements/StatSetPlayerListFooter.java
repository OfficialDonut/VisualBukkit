package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the player list footer for a player")
public class StatSetPlayerListFooter extends StatementBlock {

    public StatSetPlayerListFooter() {
        init("set player list footer of ", Player.class, " to ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setPlayerListFooter(" + arg(1) + ");";
    }
}
