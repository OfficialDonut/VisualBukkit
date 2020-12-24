package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerQuitEvent;

@Description("Sets the quit message in a PlayerQuitEvent")
public class StatSetEventQuitMessage extends StatementBlock {

    public StatSetEventQuitMessage() {
        init("set quit message to ", String.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set quit message must be used in a PlayerQuitEvent", PlayerQuitEvent.class);
    }

    @Override
    public String toJava() {
        return "event.setQuitMessage(PluginMain.color(" + arg(0) + "));";
    }
}
