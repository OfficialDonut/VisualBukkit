package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@Category(Category.ITEM)
@Description("Removes an item flag from an item")
public class StatRemoveItemFlag extends StatementBlock {

    public StatRemoveItemFlag() {
        init("remove ", ItemFlag.class, " from ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getItemMeta().removeItemFlags(" + arg(0) + ");";
    }
}
