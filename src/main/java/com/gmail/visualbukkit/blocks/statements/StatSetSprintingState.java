package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the sprinting state of a player")
public class StatSetSprintingState extends StatementBlock {

    public StatSetSprintingState() {
        init("set sprinting state of ", Player.class, " to ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setSprinting(" + arg(1) + ");";
    }
}
