package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

@Name("Player From UUID")
@Description("The player with the given UUID")
public class ExprPlayerFromUUID extends ExpressionBlock<OfflinePlayer> {

    public ExprPlayerFromUUID() {
        init("player from ", UUID.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getOfflinePlayer(" + arg(0) + ")";
    }
}