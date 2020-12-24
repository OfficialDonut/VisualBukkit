package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the sneaking state of a player")
public class StatSetSneakingState extends StatementBlock {

    public StatSetSneakingState() {
        init("set sneaking state of ", Player.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setSneaking(" + arg(1) + ");";
    }
}
