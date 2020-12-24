package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the experience level of a player")
public class StatSetPlayerExperienceLevel extends StatementBlock {

    public StatSetPlayerExperienceLevel() {
        init("set experience level of ", Player.class, " to ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setLevel(" + arg(1) + ");";
    }
}
