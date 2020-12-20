package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("Closes the inventory of a player")
public class StatCloseInventory extends StatementBlock {

    public StatCloseInventory() {
        init("close inventory of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".closeInventory();";
    }
}
