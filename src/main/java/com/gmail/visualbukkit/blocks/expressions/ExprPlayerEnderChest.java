package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Description("The ender chest inventory of a player")
public class ExprPlayerEnderChest extends ExpressionBlock<Inventory> {

    public ExprPlayerEnderChest() {
        init("ender chest of ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEnderChest()";
    }
}