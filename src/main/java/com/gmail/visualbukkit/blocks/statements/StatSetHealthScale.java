package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Sets the health scale of a player")
public class StatSetHealthScale extends StatementBlock {

    public StatSetHealthScale() {
        init("set health scale of ", Player.class, " to ", double.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setHealthScale(" + arg(1) + ");";
    }
}
