package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

@Description("The player of a skull item")
public class ExprSkullItemPlayer extends ExpressionBlock<OfflinePlayer> {

    public ExprSkullItemPlayer() {
        init("player of skull ", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "((SkullMeta)" + arg(0) + ".getItemMeta()).getOwningPlayer()";
    }
}