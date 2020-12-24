package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerJoinEvent;

@Description("Sets the join message in a PlayerJoinEvent")
public class StatSetEventJoinMessage extends StatementBlock {

    public StatSetEventJoinMessage() {
        init("set join message to ", String.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set join message must be used in a PlayerJoinEvent", PlayerJoinEvent.class);
    }

    @Override
    public String toJava() {
        return "event.setJoinMessage(PluginMain.color(" + arg(0) + "));";
    }
}
