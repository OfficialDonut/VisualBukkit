package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;

@Category(Category.PLAYER)
@Description("Sets the player of a skull block")
public class StatSetSkullBlockPlayer extends StatementBlock {

    public StatSetSkullBlockPlayer() {
        init("set player of skull ", Skull.class, " to ", OfflinePlayer.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setOwningPlayer(" + arg(1) + ");";
    }
}
