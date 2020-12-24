package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Description("The game mode of a player")
public class ExprPlayerGameMode extends ExpressionBlock<GameMode> {

    public ExprPlayerGameMode() {
        init("game mode of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getGameMode()";
    }
}