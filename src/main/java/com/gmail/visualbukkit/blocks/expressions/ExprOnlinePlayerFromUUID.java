package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import org.bukkit.entity.Player;

import java.util.UUID;

@Name("Online Player From UUID")
@Description("The online player with the given UUID")
public class ExprOnlinePlayerFromUUID extends ExpressionBlock<Player> {

    public ExprOnlinePlayerFromUUID() {
        init("online player from ", UUID.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.getPlayer(" + arg(0) + ")";
    }
}