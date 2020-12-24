package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

@Description("The inventory of a player")
public class ExprPlayerInventory extends ExpressionBlock<PlayerInventory> {

    public ExprPlayerInventory() {
        init("inventory of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory()";
    }
}