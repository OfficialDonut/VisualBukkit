package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.IO)
@Description("Sends a title message to a player")
public class StatSendTitle extends StatementBlock {

    public StatSendTitle() {
        init("send title");
        initLine("title:    ", String.class);
        initLine("subtitle: ", String.class);
        initLine("player:   ", Player.class);
        initLine("fade in:  ", int.class, " (ticks)");
        initLine("stay for: ", int.class, " (ticks)");
        initLine("fade out: ", int.class, " (ticks)");
    }

    @Override
    public String toJava() {
        return arg(2) + ".sendTitle(" + arg(0) + "," + arg(1) + "," + arg(3) + "," + arg(4) + "," + arg(5) + ");";
    }
}
