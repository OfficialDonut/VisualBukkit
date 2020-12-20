package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Description("Forces a player to run a command")
public class StatForceCommand extends StatementBlock {

    public StatForceCommand() {
        init("force ", Player.class, " to run ", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".performCommand(" + arg(1) + ");";
    }
}
