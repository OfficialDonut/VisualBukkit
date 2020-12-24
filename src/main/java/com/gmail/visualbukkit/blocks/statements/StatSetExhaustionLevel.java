package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the exhaustion level of a player")
public class StatSetExhaustionLevel extends StatementBlock {

    public StatSetExhaustionLevel() {
        init("set exhaustion level of ", Player.class, " to ", float.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setExhaustion(" + arg(1) + ");";
    }
}
