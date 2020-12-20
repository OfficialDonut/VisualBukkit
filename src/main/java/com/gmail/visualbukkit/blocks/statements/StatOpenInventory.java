package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Description("Opens an inventory to a player")
public class StatOpenInventory extends StatementBlock {

    public StatOpenInventory() {
        init("open ", Inventory.class, " to ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".openInventory(" + arg(0) + ");";
    }
}
