package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.OfflinePlayer;

@Description("Checks if a player is online")
public class ExprIsPlayerOnline extends ExpressionBlock<Boolean> {

    public ExprIsPlayerOnline() {
        init(OfflinePlayer.class, " is online");
    }

    @Override
    public String toJava() {
        return arg(0) + ".isOnline()";
    }
}