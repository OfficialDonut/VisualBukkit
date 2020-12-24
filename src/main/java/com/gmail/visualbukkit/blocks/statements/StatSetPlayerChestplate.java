package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Category(Category.PLAYER)
@Description("Sets the chestplate of a player")
public class StatSetPlayerChestplate extends StatementBlock {

    public StatSetPlayerChestplate() {
        init("set chestplate of ", Player.class, " to ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().setChestplate(" + arg(1) + ");";
    }
}
