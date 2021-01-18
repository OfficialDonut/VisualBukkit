package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.World;

import java.util.List;

@Description("The players in a world")
@SuppressWarnings("rawtypes")
public class ExprPlayersInWorld extends ExpressionBlock<List> {

    public ExprPlayersInWorld() {
        init("players in ", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPlayers()";
    }
}