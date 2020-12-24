package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.PLAYER)
@Description("Requests a player to download a resource pack from a URL")
public class StatSetResourcePack extends StatementBlock {

    public StatSetResourcePack() {
        init("set resource pack of ", Player.class, " to ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setResourcePack(" + arg(1) + ");";
    }
}
