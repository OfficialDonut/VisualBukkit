package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Description("Adds an item to an inventory")
public class StatAddToInventory extends StatementBlock {

    public StatAddToInventory() {
        init("add ", ItemStack.class, " to ", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".addItem(" + arg(0) + ");";
    }
}
