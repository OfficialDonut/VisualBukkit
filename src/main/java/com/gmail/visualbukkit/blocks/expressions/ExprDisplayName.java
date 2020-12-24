package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("The display name of a player")
public class ExprDisplayName extends ExpressionBlock<String> {

    public ExprDisplayName() {
        init("display name of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getDisplayName()";
    }
}