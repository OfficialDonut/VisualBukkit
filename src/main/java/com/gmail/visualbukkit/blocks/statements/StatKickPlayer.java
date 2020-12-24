package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Kicks a player from the server")
public class StatKickPlayer extends StatementBlock {

    public StatKickPlayer() {
        init("kick ", Player.class, " for ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".kickPlayer(PluginMain.color(" + arg(1) + "));";
    }
}
