package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Hides a player from another player")
public class StatHidePlayer extends StatementBlock {

    public StatHidePlayer() {
        init("hide ", Player.class, " from ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".hidePlayer(PluginMain.getInstance()," + arg(0) + ");";
    }
}
