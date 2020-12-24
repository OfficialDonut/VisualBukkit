package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the game mode of a player")
public class StatSetPlayerGameMode extends StatementBlock {

    public StatSetPlayerGameMode() {
        init("set game mode of ", Player.class, " to ", GameMode.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setGameMode(" + arg(1) + ");";
    }
}
