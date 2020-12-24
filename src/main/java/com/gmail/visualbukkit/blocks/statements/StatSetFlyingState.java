package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the flying state of a player")
public class StatSetFlyingState extends StatementBlock {

    public StatSetFlyingState() {
        init("set flying state of ", Player.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setFlying(" + arg(1) + ");";
    }
}
