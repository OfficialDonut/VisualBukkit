package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the fly speed of a player")
public class StatSetFlySpeed extends StatementBlock {

    public StatSetFlySpeed() {
        init("set fly speed of ", Player.class, " to ", float.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setFlySpeed(" + arg(1) + ");";
    }
}
