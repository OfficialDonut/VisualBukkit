package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Description("Sets the item in a slot of an inventory")
public class StatSetInventorySlot extends StatementBlock {

    public StatSetInventorySlot() {
        init("set inventory slot");
        initLine("inventory: ", Inventory.class);
        initLine("slot:      ", int.class);
        initLine("item:      ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setItem(" + arg(1) + "," + arg(2) + ");";
    }
}
