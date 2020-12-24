package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.OfflinePlayer;

@Description("Checks if a player has played before")
public class ExprHasPlayedBefore extends ExpressionBlock<Boolean> {

    public ExprHasPlayedBefore() {
        init(OfflinePlayer.class, " has played before");
    }

    @Override
    public String toJava() {
        return arg(0) + ".hasPlayedBefore()";
    }
}