package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The IP of a player")
public class ExprPlayerIP extends ExpressionBlock<String> {

    public ExprPlayerIP() {
        init("IP of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getAddress().getHostName()";
    }
}