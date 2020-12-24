package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.ItemStack;

@Category(Category.ITEM)
@Description("Sets the amount of an item")
public class StatSetItemAmount extends StatementBlock {

    public StatSetItemAmount() {
        init("set amount of ", ItemStack.class, " to ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setAmount(" + arg(1) + ");";
    }
}
