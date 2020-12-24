package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Un-hides a player from another player")
public class StatShowPlayer extends StatementBlock {

    public StatShowPlayer() {
        init("show ", Player.class, " to ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".showPlayer(PluginMain.getInstance()," + arg(0) + ");";
    }
}
