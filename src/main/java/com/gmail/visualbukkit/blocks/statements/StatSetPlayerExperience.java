package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the experience points of a player")
public class StatSetPlayerExperience extends StatementBlock {

    public StatSetPlayerExperience() {
        init("set experience points of ", Player.class, " to ", float.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setExp(" + arg(1) + ");";
    }
}
