package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;

@Description("Clears an inventory")
public class StatClearInventory extends StatementBlock {

    public StatClearInventory() {
        init("clear ", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".clear();";
    }
}
