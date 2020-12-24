package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the operator status of a player")
public class StatSetOperatorStatus extends StatementBlock {

    public StatSetOperatorStatus() {
        init("set operator status of ", Player.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setOp(" + arg(1) + ");";
    }
}
