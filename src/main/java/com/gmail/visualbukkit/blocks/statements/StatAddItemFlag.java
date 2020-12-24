package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@Category(Category.ITEM)
@Description("Adds an item flag to an item")
public class StatAddItemFlag extends StatementBlock {

    public StatAddItemFlag() {
        init("add ", ItemFlag.class, " to ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getItemMeta().addItemFlags(" + arg(0) + ");";
    }
}
