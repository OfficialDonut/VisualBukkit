package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the display name of a player")
public class StatSetDisplayName extends StatementBlock {

    public StatSetDisplayName() {
        init("set display name of ", Player.class, " to ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setDisplayName(" + arg(1) + ");";
    }
}
