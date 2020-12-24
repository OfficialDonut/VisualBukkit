package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

@Name("Player UUID")
@Description("The UUID of a player")
public class ExprPlayerUUID extends ExpressionBlock<UUID> {

    public ExprPlayerUUID() {
        init("UUID of ", OfflinePlayer.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getUniqueId()";
    }
}