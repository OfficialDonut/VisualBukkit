package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Description("Opens a book to a player")
public class StatOpenBook extends StatementBlock {

    public StatOpenBook() {
        init("open ", ItemStack.class, " to ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".openBook(" + arg(0) + ");";
    }
}
