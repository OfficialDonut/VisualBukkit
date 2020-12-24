package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Category(Category.PLAYER)
@Description("Sets the boots of a player")
public class StatSetPlayerBoots extends StatementBlock {

    public StatSetPlayerBoots() {
        init("set boots of ", Player.class, " to ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().setBoots(" + arg(1) + ");";
    }
}
