package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Category(Category.ITEM)
@Description("Gives an item to a player")
public class StatGiveItem extends StatementBlock {

    public StatGiveItem() {
        init("give ", ItemStack.class, " to ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getInventory().addItem(" + arg(0) + ");";
    }
}
