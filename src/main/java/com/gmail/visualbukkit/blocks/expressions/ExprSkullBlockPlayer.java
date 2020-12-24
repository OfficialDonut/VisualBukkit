package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;

@Description("The player of a skull block")
public class ExprSkullBlockPlayer extends ExpressionBlock<OfflinePlayer> {

    public ExprSkullBlockPlayer() {
        init("player of skull ", Skull.class);
    }

    @Override
    public String toJava() {
        return  arg(0) + ".getOwningPlayer()";
    }
}