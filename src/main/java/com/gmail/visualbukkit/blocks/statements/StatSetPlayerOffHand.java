package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Category(Category.PLAYER)
@Description("Sets the off hand item of a player")
public class StatSetPlayerOffHand extends StatementBlock {

    public StatSetPlayerOffHand() {
        init("set off hand item of ", Player.class, " to ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().setItemInOffHand(" + arg(1) + ");";
    }
}
