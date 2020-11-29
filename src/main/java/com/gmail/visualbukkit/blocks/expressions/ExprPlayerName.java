package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ExpressionParameter;
import org.bukkit.entity.Player;

@Description("The name of a player")
public class ExprPlayerName extends ExpressionBlock<String> {

    private ExpressionParameter player = new ExpressionParameter(Player.class);

    public ExprPlayerName() {
        init("name of ", player);
    }

    @Override
    public String toJava() {
        return player + ".getName()";
    }
}
